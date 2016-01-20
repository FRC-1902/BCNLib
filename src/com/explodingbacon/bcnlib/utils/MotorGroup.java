package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.actuators.Motor;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that lets you group together motors and mass-set their speeds. Specific motors can also be inverted.
 *
 * @author Ryan Shavell
 * @version 2016.1.19
 */

public class MotorGroup implements Motor {

	private List<Motor> motors = new ArrayList<>();
	private List<Boolean> inverts = new ArrayList<>();
    private double power = 0;
    private boolean reversed = false;

    /**
     * Creates a MotorGroup.
     */
    public MotorGroup() {}

    /**
     * Creates a MotorGroup that consists of motorArray's Motors.
     * @param motorArray The Motors to be a part of this MotorGroup.
     */
	public MotorGroup(Motor... motorArray) {
		for (Motor m : motorArray) {
			motors.add(m);
			inverts.add(false);
		}
	}

    /**
     * Creates a MotorGroup full of Motors of class "type".
     * @param type The class that the Motor objects will be.
     * @param ids The IDs of all the motors.
     * @param <T> Any class that extends Motor.
     */
    public <T extends Motor> MotorGroup(Class<T> type, Integer... ids) {
        addMotors(type, ids);
    }

    /**
     * Adds a Motor to this MotorGroup.
     * @param m The Motor to add.
     * @return This MotorGroup.
     */
    public MotorGroup addMotor(Motor m) { addMotor(m, false); return this; }

    /**
     * Adds a Motor to this MotorGroup.
     * @param m The Motor to add.
     * @param invert Whether the Motor's direction should be reversed.
     * @return This MotorGroup.
     */
    public MotorGroup addMotor(Motor m, boolean invert) {
        motors.add(m);
        inverts.add(invert);
        return this;
    }

    /**
     * Adds Motors to this MotorGroup.
     * @param moreMotors The Motors to be added.
     * @return This MotorGroup.
     */
    public MotorGroup addMotors(Motor... moreMotors) {
        for (Motor m : moreMotors) {
            motors.add(m);
            inverts.add(false);
        }
        return this;
    }

    /**
     * Adds Motors of class "type".
     * @param type The class that the Motor objects will be.
     * @param ids The IDs of the motors.
     * @param <T> Any class that extends Motor.
     * @return This MotorGroup.
     */
    public <T extends Motor> MotorGroup addMotors(Class<T> type, Integer... ids) {
        try {
            Constructor[] allCons = type.getConstructors();
            if (allCons.length > 0) {
                Constructor con = allCons[0];
                for (int i = 0; i < ids.length; i++) {
                    T motor = (T) con.newInstance(ids[i]);
                    motors.add(motor);
                    inverts.add(false);
                }
            } else {
                System.out.println("[ERROR] Motor type that doesn't have any constructors given to MotorGroup! WHAT?");
            }
        } catch (Exception e) {}
        return this;
    }

    /**
     * Sets the invert statuses of all the Motors in this MotorGroup.
     * @param newInverts All the new invert statuses. Must be the same length as the motor list.
     * @return This MotorGroup.
     */
    public MotorGroup setInverts(Boolean... newInverts) {
        if (newInverts.length == motors.size()) {
            inverts.clear();
            Collections.addAll(inverts, newInverts);
        } else {
            System.out.println("[ERROR] MotorGroup.setInverts() got an array of inverts that is not the same size as the motor list!");
        }
        return this;
    }

    @Override
	public void setPower(double d) {
		int index = 0;
		for (Motor m : motors) {
            double speed = inverts.get(index) ? -d : d; //Inverts the speed based off of that motor's invert value
			m.setPower(reversed ? -speed : speed); //Sets the motor's speed and possibly inverts it if the MotorGroup as a whole is inverted
			index++;
		}
        power = d;
	}

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    /**
     * Gets all the motors in this MotorGroup.
     * @return All the motors in this MotorGroup.
     */
    public List<Motor> getMotors() {
        return new ArrayList<>(motors);
    }

    /**
     * Gets how many motors are in this MotorGroup.
     * @return How many motors are in this MotorGroup.
     */
    public int getMotorCount() {
        return motors.size();
    }
}
