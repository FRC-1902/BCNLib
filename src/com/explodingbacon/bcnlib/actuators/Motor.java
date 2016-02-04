package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.ExtendableRobot;
import com.explodingbacon.bcnlib.sensors.MotorEncoder;
import com.explodingbacon.bcnlib.utils.NetTuner;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan Shavell
 * @version 2016.1.26
 */

public class Motor {

    protected SpeedController sc = null;
    protected int channel = -1;
    protected boolean reverse = false;
    protected boolean isTuning = false;
    protected String tuningKey = "";
    protected String name = "";
    protected MotorEncoder encoder = null;
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
        init();
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
        init();
    }

    /**
     * The constructor to be used when an implementation of Motor does not require the use of a SpeedController. If this
     * constructor is being used, you'll need to override getPower() and setPower() to not use the "sc" variable.
     */
    protected Motor() {
        init();
    }

    /**
     * Initializes this Motor.
     */
    private void init() {
        allMotors.add(this);
    }

    /**
     * Sets the name of this Motor.
     * @param n The new name of the Motor.
     * @return This Motor (for method chaining and variable saving)
     */
    public Motor setName(String n) {
        name = n;
        return this;
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
        SmartDashboard.putNumber(getSBKey(), getPower());
    }

    /**
     * Sets the current power of this Motor. Motor power ranges from 1 to -1. This method should only be called from
     * BCNLib packages, since it overrides any restrictions.
     *
     * @param t A valid NetTuner, for access controllers.
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
        tune(getSBKey());
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
     * Gets the MotorEncoder for this Motor. Only for CANTalons currently.
     * @return
     */
    public MotorEncoder getEncoder() {
        if (encoder == null) {
            if (sc instanceof CANTalon) {
                encoder = new MotorEncoder((CANTalon) sc);
            } else {
                System.out.println("[ERROR] Attempted to get a MotorEncoder from a Motor that does not have an encoder!");
                return null;
            }
        }
        return encoder;
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
     * Gets the SmartDashboard key for this Motor.
     * @return The SmartDashboard key for this Motor.
     */
    public String getSBKey() {
        if (!name.equals("")) {
            return name;
        } else {
            return "" + getMotorClass().getSimpleName() + "_" + channel;
        }
    }

    /**
     * Gets all the Motors that have been created.
     * @return All the Motors that have been created.
     */
    public static List<Motor> getAllMotors() {
        return new ArrayList<>(allMotors);
    }
}
