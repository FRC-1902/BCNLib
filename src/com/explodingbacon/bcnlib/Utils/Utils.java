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
        if (val == 0) return 0; //Needed to avoid divide by zero exception
        val = Math.abs(val) < min ? 0 : val;
        val = Math.abs(val) > max ? max * Math.abs(val) / val : val;
        return val;
    }
}
