package com.explodingbacon.bcnlib.networking;

import com.explodingbacon.bcnlib.controllers.JoystickInterface;
import com.explodingbacon.bcnlib.framework.AbstractOI;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 */
public class NetJoystick implements JoystickInterface {
    protected double xVal, yVal;
    protected String key;

    /**
     * Default Constructor
     *
     * @param key A unique key to refer to this NetJoystick
     */
    public NetJoystick(String key) {
        xVal = 0;
        yVal = 0;
        this.key = key;
        AbstractOI.addNetJoystick(this);
    }

    /**
     * Refresh the value of this NetJoystick. Automatically handled by AbstractOI.
     */
    public void refresh() {
        double x = AbstractOI.table.getNumber(key + "_x", 0d);
        double y = AbstractOI.table.getNumber(key + "_y", 0d);

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
