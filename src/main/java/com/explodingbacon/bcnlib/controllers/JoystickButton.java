package com.explodingbacon.bcnlib.controllers;

/**
 * An implementation of Button for Buttons on a Joystick.
 *
 * @author Ryan Shavell
 * @version 2016.9.10
 */

public class JoystickButton implements Button {

    private edu.wpi.first.wpilibj.buttons.JoystickButton button;
    private boolean last = false;

    /**
     * Creates a JoystickButton that corresponds to a physical button on j.
     *
     * @param j The Joystick this Button is on.
     * @param buttonID The ID of this Button.
     */
    public JoystickButton(Joystick j, int buttonID) {
        button = new edu.wpi.first.wpilibj.buttons.JoystickButton(j.getWPIJoystick(), buttonID);
    }

    /**
     * Gets the current state of the button.
     *
     * @return The current state of the button.
     */
    public boolean get() {
        return last = button.get();
    }

    /**
     * Gets the state the button was in last time it was checked.
     *
     * @return The state the button was in last time it was checked.
     */
    public boolean getPrevious() {
        return last;
    }
}
