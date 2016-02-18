package com.explodingbacon.bcnlib.event;

import com.explodingbacon.bcnlib.controllers.Button;

/**
 * An event that is fired when a Button is released.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class ButtonReleaseEvent extends Event {

    private Button button;

    /**
     * Creates a ButtonReleaseEvent for a certain Button.
     * @param b The Button.
     */
    public ButtonReleaseEvent(Button b) { button = b; }

    /**
     * Gets the Button affiliated with this event.
     * @return The Button affiliated with this event.
     */
    public Button getButton() {
        return button;
    }
}