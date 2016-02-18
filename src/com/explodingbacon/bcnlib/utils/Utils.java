package com.explodingbacon.bcnlib.utils;

import java.lang.management.ManagementFactory;

/**
 * A Utility class that contains functions that are often used in various parts of robot programming.
 *
 * @author Ryan Shavell
 * @version 2016.2.9
 */

public class Utils {

    private static String newLine = null;

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
        val = Math.abs(val) > max ? max * sign(val) : val;
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
     * Gets a distance from a certain object. TODO: Make a version of this that works for other cameras with different FOVs and stuff
     * @param sizeInPx The size of the object in pixels.
     * @return The distance from the object.
     */
    public static double getDistanceFromPx(double sizeInPx) {
        return 10.25/Math.tan(Math.toRadians((62.5/1280)*sizeInPx));
    }

    /**
     * Rounds a Double to an Integer.
     * @param d The Double to be rounded.
     * @return The Integer.
     */
    public static int round(double d) {
        return (int) Math.round(d);
    }

    /**
     * Gets the amount of Threads running.
     * @return The amount of Threads running.
     */
    public static int getThreadCount() {
        return ManagementFactory.getThreadMXBean().getThreadCount();
    }

    /**
     * Gets the new line character for the current operating system.
     * @return The new line character for the current operating system.
     */
    public static String newLine() {
        if (newLine == null) {
            newLine = System.getProperty("line.separator");
        }
        return newLine;
    }

    /**
     * Runs code in it's own separate Thread. TODO: Check if this works properly
     * @param r The code to be run.
     */
    public static void runInOwnThread(Runnable r) {
        CodeThread c = new CodeThread(false, r);
        c.start();
    }
}
