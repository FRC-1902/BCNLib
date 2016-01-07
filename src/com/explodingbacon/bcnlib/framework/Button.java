package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Button {

    private JoystickButton button;
    private boolean last = false;

    public Button(Joystick j, int buttonID) {
        button = new JoystickButton(j, buttonID);
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
