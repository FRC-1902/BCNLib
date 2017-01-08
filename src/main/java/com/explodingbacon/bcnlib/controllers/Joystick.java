package com.explodingbacon.bcnlib.controllers;

import com.explodingbacon.bcnlib.framework.Log;

/**
 * Implementation of JoystickInterface that wrappers around WPILib's Joystick object.
 *
 * @author Ryan Shavell
 * @version 2016.9.10
 */
public class Joystick implements JoystickInterface {

    protected edu.wpi.first.wpilibj.Joystick joy;

    /**
     * Standard constructor for making a Joystick.
     *
     * @param port The USB port the Joystick is plugged into.
     */
    public Joystick(int port) {
        joy = new edu.wpi.first.wpilibj.Joystick(port);
    }

    /**
     * Initializes a Joystick without creating an internal WPILib Joystick. You need to override the other Joystick
     * methods if you want to use this constructor.
     */
    public Joystick() {
        joy = null;
    }

    protected edu.wpi.first.wpilibj.Joystick getWPIJoystick() {
        return joy;
    }

    public double getRawAxis(int axis) {
        return joy.getRawAxis(axis);
    }

    @Override
    public double getX() {
        if (joy != null)
            return joy.getX();
        else {
            Log.e("Called Joystick.getX() when the internal WPI joystick is null and/or the method has not been overridden!");
            return -2;
        }
    }

    @Override
    public double getY() {
        if (joy != null)
            return joy.getY();
        else {
            Log.e("Called Joystick.getY() when the internal WPI joystick is null and/or the method has not been overridden!");
            return -2;
        }
    }

    @Override
    public double getZ() {
        if (joy != null)
            return joy.getZ();
        else {
            Log.e("Called Joystick.getZ() when the internal WPI joystick is null and/or the method has not been overridden!");
            return -2;
        }
    }
}