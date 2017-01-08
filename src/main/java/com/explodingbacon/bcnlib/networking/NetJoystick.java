package com.explodingbacon.bcnlib.networking;

import com.explodingbacon.bcnlib.controllers.JoystickInterface;
import com.explodingbacon.bcnlib.framework.AbstractOI;

/**
 * A class for reading from NetJoysticks.
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public class NetJoystick implements JoystickInterface {
    protected double xVal, yVal, zVal;
    protected String key;

    /**
     * Default Constructor
     *
     * @param key A unique key to refer to this NetJoystick
     */
    public NetJoystick(String key) {
        xVal = 0;
        yVal = 0;
        zVal = 0;
        this.key = key;
        AbstractOI.addNetJoystick(this);
    }

    /**
     * Refresh the value of this NetJoystick. Automatically handled by AbstractOI.
     */
    public void refresh() {
        double x = AbstractOI.table.getNumber(key + "_x", 0d);
        double y = AbstractOI.table.getNumber(key + "_y", 0d);
        double z = AbstractOI.table.getNumber(key + "_z", 0d);

        x = Math.max(-1, x);
        y = Math.max(-1, y);
        z = Math.max(-1, z);
        x = Math.min(x, 1);
        y = Math.min(y, 1);
        z = Math.min(z, 1);

        xVal = x;
        yVal = y;
        zVal = z;
    }

    @Override
    public double getX() {
        return xVal;
    }

    @Override
    public double getY() {
        return yVal;
    }

    @Override
    public double getZ() {
        return zVal;
    }

}
