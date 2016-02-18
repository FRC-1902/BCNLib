package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.utils.Utils;
import edu.wpi.first.wpilibj.SpeedController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that lets you group together motors and mass-set their speeds. Specific motors can also be inverted.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class MotorGroup extends Motor {

    private List<Motor> motors = new ArrayList<>();
    private List<Boolean> inverts = new ArrayList<>();
    private double power = 0;
    private boolean reversed = false;

    /**
     * Creates a MotorGroup.
     */
    public MotorGroup() {
        super();
    }

    /**
     * Creates a MotorGroup that consists of motorArray's Motors.
     *
     * @param motorArray The Motors to be a part of this MotorGroup.
     */
    public MotorGroup(Motor... motorArray) {
        super();
        for (Motor m : motorArray) {
            motors.add(m);
            inverts.add(false);
        }
    }

    /**
     * Creates a MotorGroup full of Motors of class "type".
     *
     * @param type The class that the Motor objects will be.
     * @param ids  The IDs of all the motors.
     * @param <T>  Any class that extends Motor.
     */
    public <T extends SpeedController> MotorGroup(Class<T> type, Integer... ids) {
        super();
        addMotors(type, ids);
    }

    /**
     * Adds a Motor to this MotorGroup.
     *
     * @param m The Motor to add.
     * @return This MotorGroup.
     */
    public MotorGroup addMotor(Motor m) {
        addMotor(m, false);
        return this;
    }

    /**
     * Adds a Motor to this MotorGroup.
     *
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
     *
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
     *
     * @param type The class that the Motor objects will be.
     * @param ids  The IDs of the motors.
     * @param <T>  Any class that extends Motor.
     * @return This MotorGroup.
     */
    public <T extends SpeedController> MotorGroup addMotors(Class<T> type, Integer... ids) {
        for (int i = 0; i < ids.length; i++) {
            motors.add(new Motor(type, ids[i]));
            inverts.add(false);
        }
        return this;
    }

    /**
     * Clears the list of Motors.
     */
    public void clearMotors() {
        motors.clear();
    }

    /**
     * Sets the invert statuses of all the Motors in this MotorGroup.
     *
     * @param newInverts All the new invert statuses. Must be the same length as the motor list.
     * @return This MotorGroup.
     */
    public MotorGroup setInverts(Boolean... newInverts) {
        if (newInverts.length == motors.size()) {
            inverts.clear();
            Collections.addAll(inverts, newInverts);
        } else {
            Log.e("MotorGroup.setInverts() got an array of inverts that is not the same size as the motor list!");
        }
        return this;
    }

    /**
     * Tests each Motor in this MotorGroup one at a time.
     * @param power The speed the Motors will run at while being tested.
     */
    public void testOneAtATime(double power, double timeOn) { //TODO: give better name :)
        Utils.runInOwnThread(() -> {
            testOneAtATimeBlocking(power, timeOn);
        });
    }

    public void testOneAtATimeBlocking(double power, double timeOn) {
        for (Motor m : motors) {
            try {
                m.setPower(power);
                Thread.sleep(Math.round(timeOn * 1000));
                m.setPower(0);
                Thread.sleep(1000);
            } catch (Exception e) {}
        }
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

    @Override
    public void stopMotor() {
        motors.forEach(Motor::stopMotor);
    }

    /**
     * Gets all the motors in this MotorGroup.
     *
     * @return All the motors in this MotorGroup.
     */
    public List<Motor> getMotors() {
        return new ArrayList<>(motors);
    }

    /**
     * Gets how many motors are in this MotorGroup.
     *
     * @return How many motors are in this MotorGroup.
     */
    public int getMotorCount() {
        return motors.size();
    }
}
