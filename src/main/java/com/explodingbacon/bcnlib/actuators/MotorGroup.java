package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.utils.Utils;
import edu.wpi.first.wpilibj.SpeedController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * A class that lets you group together motors and mass-set their speeds. Specific motors can also be inverted.
 *
 * @author Ryan Shavell
 * @version 2016.9.10
 */

public class MotorGroup extends Motor {

    private List<Motor> motors = new ArrayList<>();
    private List<Boolean> inverts = new ArrayList<>();
    private double power = 0;
    private boolean reversed = false;
    private boolean logChanges = false;

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
        addMotors(motorArray);
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
        for (Integer id : ids) {
            motors.add(new Motor(type, id));
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
     * Calls code on each Motor in this MotorGroup.
     * @param c The code to call.
     */
    public void forEach(Consumer<Motor> c) {
        motors.forEach(c);
    }

    /**
     * Tests each Motor in this MotorGroup.
     *
     * @param power The speed the Motor will run at while being tested.
     * @param timeOn How long the Motor will be on while being tested.
     */
    public void testEach(double power, double timeOn) {
        Utils.runInOwnThread(() -> testEachWait(power, timeOn));
    }

    /**
     * Tests each Motor in this MotorGroup, and freezes the Thread until the test is complete.
     *
     * @param power The speed the Motor will run at while being tested.
     * @param timeOn How long the Motor will be on while being tested.
     */
    public void testEachWait(double power, double timeOn) {
        for (Motor m : motors) {
            try {
                m.setPower(power);
                Thread.sleep(Math.round(timeOn * 1000));
                m.setPower(0);
                Thread.sleep(1000);
            } catch (Exception e) {
                Log.e("MotorGroup.testEachWait() exception!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setBrakeMode(boolean b) {
        forEach((m) -> m.setBrakeMode(b));
    }

    /**
     * Checks if this MotorGroup is logging changes in power.
     *
     * @return If this MotorGroup is logging changes in power.
     */
    public boolean isLoggingChanges() {
        return logChanges;
    }

    /**
     * Sets if this MotorGroup should be logging changes in power.
     *
     * @param b If this MotorGroup is logging changes in power.
     */
    public void setLoggingChanges(boolean b) {
        logChanges = b;
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

    @Override
    public void setPower(double d) {
        int index = 0;
        for (Motor m : motors) {
            double speed = inverts.get(index) ? -d : d; //Inverts the speed based off of that motor's invert value
            m.setPower(reversed ? -speed : speed); //Sets the motor's speed and possibly inverts it if the MotorGroup as a whole is inverted
            index++;
        }
        if (d != power && logChanges) {
            Log.d("MotorGroup \"" + getName() + "\"'s power has been set to \"" + d + "\".");
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
        forEach(Motor::stopMotor);
    }

}
