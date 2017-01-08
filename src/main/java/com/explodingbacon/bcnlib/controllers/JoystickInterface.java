package com.explodingbacon.bcnlib.controllers;

/**
 * An interface for Joysticks.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */
public interface JoystickInterface {

    /**
     * Gets the X axis of the Joystick.
     * @return The X axis of the Joystick.
     */
    double getX();

    /**
     * Gets the Y axis of the Joystick.
     * @return The Y axis of the Joystick.
     */
    double getY();

    /**
     * Gets the Z axis of the Joystick.
     * @return The Z axis of the Joystick.
     */
    double getZ();

}
