package com.explodingbacon.bcnlib.utils;

/**
 * A Utility class that contains functions that are often used in various parts of robot programming.
 *
 * @author Ryan Shavell
 * @version 2016.2.6
 */

public class Utils {

    /**
     * Taking a value, bound it with a max and a deadzone respecting the sign of the input. Useful for sending values to motors.
     *
     * @param val The value to be compared
     * @param min The minimum value to accept. Below this, it returns 0.
     * @param max The maximum value to accept. Above this, it returns this.
     * @return A value scaled by <code>min</code> and <code>max</code>.
     */
    public static double minMax(double val, double min, double max) {
        if (val == 0) return 0; //Needed to avoid DivideByZeroException
        val = Math.abs(val) < min ? 0 : val;
        val = Math.abs(val) > max ? max * (Math.abs(val) / val) : val;
        return val;
    }

    /**
     * Applies a deadzone to a value. If the value is bigger than the value, the value is returned. Otherwise, 0 is returned.
     * Useful for deadzones on motor/joystick values.
     *
     * @param d The value to be deadzoned.
     * @param deadzone The deadzone.
     * @return If the value is bigger than the value, the value is returned. Otherwise, 0 is returned.
     */
    public static double deadzone(double d, double deadzone) {
        return Math.abs(d) > Math.abs(deadzone) ? d : 0;
    }

    /**
     * Returns 1 if d is positive or 0, returns -1 if d is negative.
     * @param d The number who's sign we're checking.
     * @return 1 if d is positive or 0, -1 if d is negative.
     */
    public static double sign(double d) {
        double sign = 1;
        if (d != 0) {
            sign = (Math.abs(d) / d);

        }
        return sign;
    }

    /**
     * Rounds a Double to an Integer.
     * @param d The Double to be rounded.
     * @return The Integer.
     */
    public static int round(double d) {
        return (int) Math.round(d);
    }
}
