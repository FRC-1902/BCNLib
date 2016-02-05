package com.explodingbacon.bcnlib.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that groups together Buttons.
 *
 * @author Ryan Shavell
 * @version 2016.2.4
 */

public class ButtonGroup implements Button {

    private List<Button> buttons = new ArrayList<>();

    public ButtonGroup(Button...b) {
        Collections.addAll(buttons, b);
    }

    /**
     * Returns true if any Button in this ButtonGroup is true.
     * @return True if any Button in this ButtonGroup is true.
     */
    public boolean getAny() {
        for (Button b : buttons) {
            if (b.get()) return true;
        }
        return false;
    }

    /**
     * Returns true if all Buttons in this ButtonGroup are true.
     * @return True if all Buttons in this ButtonGroup are true.
     */
    public boolean getAll() {
        for (Button b : buttons) {
            if (!b.get()) return false;
        }
        return true;
    }

    @Override
    public boolean get() {
        return getAny();
    }
}
