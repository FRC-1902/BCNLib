package com.explodingbacon.bcnlib.framework;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 */
public class NetJoystick {
    private double xVal, yVal;
    private String key;

    /**
     * Default Constructor
     *
     * @param key A unique key to refer to this NetJoystick
     */
    public NetJoystick(String key) {
        xVal = 0;
        yVal = 0;
        this.key = key;
        Robot.getRobot().oi.addNetJoystick(this);
    }

    /**
     * Refresh the value of this NetJoystick. Automatically handled by OI.
     */
    public void refresh() {
        double x = OI.netTable.getNumber(key + "_x", 0);
        double y = OI.netTable.getNumber(key + "_y", 0);

        x = Math.max(-1, x);
        y = Math.max(-1, y);
        x = Math.min(x, 1);
        y = Math.min(y, 1);

        xVal = x;
        yVal = y;
    }

    /**
     * Get the x value of this NetJoystick
     *
     * @return The x value of this NetJoystick
     */
    public double getX() {
        return xVal;
    }

    /**
     * Get the y value of this NetJoystick
     *
     * @return They y value of this NetJoystick
     */
    public double getY() {
        return yVal;
    }

}
