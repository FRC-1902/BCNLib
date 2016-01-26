package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.ExtendableRobot;
import com.explodingbacon.bcnlib.framework.NetTuner;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan Shavell
 * @version 2016.1.23
 */

public class Motor {

    protected SpeedController sc = null;
    protected int channel = -1;
    protected boolean reverse = false;
    protected boolean isTuning = false;
    protected String tuningKey = "";
    private static List<Motor> allMotors = new ArrayList<>();

    /**
     * Standard constructor for Motor.
     * @param clazz The class of this Motor.
     * @param channel The channel (PWM or CAN) of this Motor.
     * @param <T> A class that extends SpeedController.
     */
    public <T extends SpeedController> Motor(Class<T> clazz, int channel) {
        this.channel = channel;
        try {
            Constructor[] constructors = clazz.getConstructors();
            if (constructors.length > 0) {
                for (Constructor c : constructors) {
                    Class[] paramTypes = c.getParameterTypes();
                    if (paramTypes.length == 1 && (paramTypes[0] == Integer.class || paramTypes[0] == int.class)) {
                        sc = (SpeedController) c.newInstance(channel);
                        break;
                    }
                }
                if (sc == null) System.out.println("The SpeedController class given to Motor.java does not have a " +
                        "constructor that accepts an Integer as its only argument!");
            }
        } catch (Exception ignored) {
        }
        allMotors.add(this);
    }

    /**
     * Constructor a Motor using an existing SpeedController object.
     * @param s The SpeedController object.
     */
    public Motor(SpeedController s) {
        sc = s;
        if (s.getInverted()) {
            s.setInverted(false);
            setReversed(true);
        }
        allMotors.add(this);
    }

    /**
     * The constructor to be used when an implementation of Motor does not require the use of a SpeedController. If this
     * constructor is being used, you'll need to override getPower() and setPower() to not use the "sc" variable.
     */
    protected Motor() {
        allMotors.add(this);
    }

    /**
     * Gets the current power of this Motor. Motor power ranges from 1 to -1.
     * @return The current power of this Motor.
     */
    public double getPower() {
        return sc.get();
    }

    /**
     * Sets the current power of this Motor. Motor power ranges from 1 to -1.
     * @param d The new power of this Motor.
     */
    public void setPower(double d) {
        if (isTuning) return;
        sc.set(reverse ? -d : d);
        SmartDashboard.putNumber(getDefaultSDBKey(), getPower());
    }

    /**
     * Sets the current power of this Motor. Motor power ranges from 1 to -1. This method should only be called from
     * BCNLib packages, since it overrides any restrictions.
     *
     * @param t A valid NetTuner, for access control.
     * @param d The new power of this Motor
     */
    public void setPower(NetTuner t, double d) {
        if (t == null) return;
        sc.set(reverse ? -d : d);
    }

    /**
     * Checks if this Motor is reversed.
     * @return If this Motor is reversed.
     */
    public boolean isReversed() {
        return reverse;
    }

    /**
     * Sets this Motor's reversal status.
     * @param b Whether or not this Motor should be reversed.
     */
    public void setReversed(boolean b) {
        reverse = b;
    }

    /**
     * Checks if this Motor is in tuning mode.
     * @return If this Motor is in tuning mode.
     */
    public boolean isTuning() {
        return isTuning;
    }

    /**
     * Puts this Motor into tuning mode.
     */
    public void tune() {
        tune(getDefaultSDBKey());
    }

    /**
     * Puts this Motor into tuning mode under a specific key.
     * @param k The specific key.
     */
    public void tune(String k) {
        ExtendableRobot.getRobot().oi.tuner.tune(k, this);
        isTuning = true;
        tuningKey = k;
    }

    /**
     * Takes this Motor out of tuning mode.
     */
    public void stopTuning() {
        ExtendableRobot.getRobot().oi.tuner.stopTune(tuningKey);
        isTuning = false;
        tuningKey = "";
    }

    /**
     * Gets this Motor's class.
     * @return This Motor's class.
     */
    public Class getMotorClass() {
        if (sc != null) {
            return sc.getClass();
        } else {
            return getClass();
        }
    }

    /**
     * Gets the default SmartDashboard key for this Motor.
     * @return The default SmartDashboard key for this Motor.
     */
    public String getDefaultSDBKey() {
        return "" + getMotorClass().getSimpleName() + "_" + channel;
    }

    /**
     * Gets all the Motors that have been created.
     * @return All the Motors that have been created.
     */
    public static List<Motor> getAllMotors() {
        return new ArrayList<>(allMotors);
    }
}
