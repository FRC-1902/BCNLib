package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.Joystick;

/**
 * @author Ryan Shavell
 * @version 2016.1.14
 */

public class JoystickButton implements Button {

    private edu.wpi.first.wpilibj.buttons.JoystickButton button;
    private boolean last = false;

    public JoystickButton(Joystick j, int buttonID) {
        button = new edu.wpi.first.wpilibj.buttons.JoystickButton(j, buttonID);
    }

    /**
     * Gets the current state of the button.
     * @return The current state of the button.
     */
    public boolean get() {
        return last = button.get();
    }

    /**
     * Gets the state the button was in last time it was checked.
     * @return The state the button was in last time it was checked.
     */
    public boolean getPrevious() {
        return last;
    }
}
