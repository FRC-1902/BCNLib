package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.framework.Mode;
import com.explodingbacon.bcnlib.framework.RobotCore;

import java.lang.management.ManagementFactory;
import java.util.function.BooleanSupplier;

/**
 * A Utility class that contains helpful functions that don't really fit in any other classes.
 *
 * @author Ryan Shavell
 * @version 2016.3.7
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
     *
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
     *
     * @param sizeInPx The size of the object in pixels.
     * @return The distance from the object.
     */
    public static double getDistanceFromPx(double sizeInInches, double sizeInPx) {
        return (0.5*sizeInInches)/Math.tan(Math.toRadians(FOV * (sizeInPx/IMAGE_WIDTH)));
    }

    private static double IMAGE_WIDTH = 640;
    private static double GOAL_TARGET_WIDTH = 20.5; //Width in inches of object being referenced
    private static double FOV = 59.7; //Was 31.25 and 50

    /**
     * Gets how many degrees the Robot needs to turn to get x in the center of the camera's vision.
     *
     * @param x The coordinate.
     * @return How many degrees the Robot needs to turn to get x in the center of the camera's vision.
     */
    public static double getDegreesToTurn(double x) {
        return getDegreesToTurn(x, IMAGE_WIDTH / 2);
    }

    /**
     * Gets how many degrees the Robot needs to turn to get x to target.
     *
     * @param x The coordinate.
     * @param target Where you want x to wind up.
     * @return How many degrees the Robot needs to turn to get x to target.
     */
    public static double getDegreesToTurn(double x, double target) {
        double distance = x - target; //TODO: check if sign is correct
        if (distance != 0) {
            double percent = distance / IMAGE_WIDTH;
            Log.d("Distance: " + distance + ", Target: " + target + ", Percent: " + (percent * 100));
            return FOV * percent;
        } else {
            return 0;
        }
    }

    /**
     * Rounds a double down to a certain amount of decimal places.
     *
     * @param number The number to be rounded.
     * @param decimalPlaces The decimal places.
     * @return The double rounded down to a certain amount of decimal places.
     */
    public static double roundToDecimals(double number, int decimalPlaces) {
        int pow = (int) Math.pow(10, decimalPlaces);
        return Math.round(number * pow)/pow;
    }


    /**
     * Rounds a Double to an Integer.
     *
     * @param d The Double to be rounded.
     * @return The Integer.
     */
    public static int round(double d) {
        return (int) Math.round(d);
    }

    /**
     * Gets the amount of Threads running.
     *
     * @return The amount of Threads running.
     */
    public static int getThreadCount() {
        return ManagementFactory.getThreadMXBean().getThreadCount();
    }

    /**
     * Gets the new line character for the current operating system.
     *
     * @return The new line character for the current operating system.
     */
    public static String newLine() {
        if (newLine == null) {
            newLine = System.getProperty("line.separator");
        }
        return newLine;
    }

    /**
     * Checks if a Class is an Integer or int class.
     *
     * @param c The Class to be checked.
     * @return If a Class is an Integer or int class.
     */
    public static boolean isIntClass(Class c) {
        return c == Integer.class || c == int.class;
    }

    /**
     * Runs code in it's own separate Thread. TODO: Check if this works properly
     *
     * @param r The code to be run.
     */
    public static void runInOwnThread(Runnable r) {
        CodeThread c = new CodeThread(false, r);
        c.start();
    }

    /**
     * Freezes the thread until the BooleanSupplier returns true.
     *
     * @param b The BooleanSupplier.
     * @return True.
     */
    public static boolean waitFor(BooleanSupplier b) {
        return waitFor(b, -1);
    }

    /**
     * Freezes the thread until the BooleanSupplier returns true, or the timeout is reached.
     *
     * @param b The BooleanSupplier.
     * @param timeout How many seconds the Thread is allowed to be frozen before it unfreezes regardless of the BooleanSupplier's value. Set to -1 if you don't want a timeout.
     * @return True if the wait ended normally, false if it was ended due to reaching the timeout.
     */
    public static boolean waitFor(BooleanSupplier b, double timeout) {
        double startTime = System.currentTimeMillis();
        double goalTime = startTime + (timeout * 1000);
        boolean noTimeOut = false;
        boolean startedEnabled = RobotCore.isEnabled();
        Mode startMode = RobotCore.getMode();
        while (!b.getAsBoolean() && (noTimeOut = (System.currentTimeMillis() < goalTime || timeout == -1))) {
            if (startedEnabled && !RobotCore.isEnabled() || startMode != RobotCore.getMode()) { //We want these loops to stop if we disable in the middle of them or change modes
                noTimeOut = false;
                Log.i("A wait loop was cancelled due to the robot being disabled in the middle of it OR from the mode being changed (i.e. auto -> teleop)");
                break;
            }
            try {
                Thread.sleep(25);
            } catch (Exception e) {
                Log.e("Utils.waitFor() error!");
                e.printStackTrace();
                break;
            }
        }
        return noTimeOut;
    }
}
