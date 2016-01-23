package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.ExtendableRobot;
import com.explodingbacon.bcnlib.framework.NetTuner;
import edu.wpi.first.wpilibj.SpeedController;

import java.lang.reflect.Constructor;

/**
 * @author Ryan Shavell
 * @version 2016.1.23
 */

public class Motor {

    protected SpeedController sc = null;
    protected boolean reverse = false;
    protected boolean isTuning = false;

    /**
     * Standard constructor for Motor.
     * @param clazz The class of this Motor.
     * @param channel The channel (PWM or CAN) of this Motor.
     * @param <T> A class that extends SpeedController.
     */
    public <T extends SpeedController> Motor(Class<T> clazz, int channel) {
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
    }

    /**
     * Constructor a Motor using an existing SpeedController object.
     * @param s The SpeedController object.
     */
    public Motor(SpeedController s) {
        sc = s;
    }

    /**
     * The constructor to be used when an implementation of Motor does not require the use of a SpeedController. If this
     * constructor is being used, you'll need to override getPower() and setPower() to not use the "sc" variable.
     */
    protected Motor() {}

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

    public void tune(String key) {
        ExtendableRobot.getRobot().oi.tuner.tune(key, this);
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
}
