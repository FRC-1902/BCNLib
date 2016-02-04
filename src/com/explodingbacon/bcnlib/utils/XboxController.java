package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.control.Button;
import com.explodingbacon.bcnlib.control.JoystickButton;

/**
 * An extension of GameController made specific for XboxController controllers.
 *
 * @author Ryan Shavell
 * @version 2016.2.2
 */

public class XboxController extends GameController {

    public Button a, b, x, y;
    public Button start, select;
    public Button leftBumper, rightBumper;
    public Button leftJoyButton, rightJoyButton;

    public XboxController(int port) {
        super(port);

        a = new JoystickButton(this, 1);
        b = new JoystickButton(this, 2);
        x = new JoystickButton(this, 3);
        y = new JoystickButton(this, 4);

        start = new JoystickButton(this, 8);
        select = new JoystickButton(this, 7);

        leftBumper = new JoystickButton(this, 5);
        rightBumper = new JoystickButton(this, 6);

        leftJoyButton = new JoystickButton(this, 9);
        rightJoyButton = new JoystickButton(this, 10);
    }

    /**
     * Gets the value of the left trigger.
     * @return The value of the left trigger.
     */
    public double getLeftTrigger() {
        return getRawAxis(2);
    }

    /**
     * Gets the value of the right trigger.
     * @return The value of the right trigger.
     */
    public double getRightTrigger() {
        return getRawAxis(3);
    }

    @Override
    public double getX2() {
        return getRawAxis(4);
    }

    @Override
    public double getY2() {
        return getRawAxis(5);
    }
}