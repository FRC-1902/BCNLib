package com.explodingbacon.bcnlib.utils;

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
}
