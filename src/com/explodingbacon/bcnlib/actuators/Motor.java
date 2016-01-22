package com.explodingbacon.bcnlib.actuators;

import edu.wpi.first.wpilibj.SpeedController;
import java.lang.reflect.Constructor;

/**
 * @author Ryan Shavell
 * @version 2016.1.21
 */

public class Motor {

    protected SpeedController sc = null;
    protected boolean reverse = false;

    /**
     * Standard constructor for Motor.
     * @param clazz The class of this Motor.
     * @param channel The channel (PWM or CAN) of this Motor.
     * @param <T> A class that extends SpeedController.
     */
    public <T extends SpeedController> Motor(Class<T> clazz, int channel) {
        Constructor[] constructors = clazz.getConstructors();
        if (constructors.length > 0) {
            try {
                sc = (SpeedController) constructors[0].newInstance(channel);
            } catch (Exception e) {}
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
        sc.set(d);
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
}
