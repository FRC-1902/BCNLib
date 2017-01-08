package com.explodingbacon.bcnlib.controllers;

/**
 * An extension of GameController made specific for Xbox controllers.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
 */

public class XboxController extends GameController {

    public Button a, b, x, y;
    public Button start, select;
    public Button leftBumper, rightBumper;
    public Button leftJoyButton, rightJoyButton;
    public Button leftTrigger, rightTrigger;
    public ButtonGroup bumpers;
    public ButtonGroup triggers;

    private static final double TRIGGER_PRESSED = 0.1;

    /**
     * Creates an XboxController on a port.
     * @param port The port.
     */
    public XboxController(int port) {
        super(port);

        a = new JoystickButton(this, 1);
        b = new JoystickButton(this, 2);
        x = new JoystickButton(this, 3);
        y = new JoystickButton(this, 4);

        select = new JoystickButton(this, 7);
        start = new JoystickButton(this, 8);

        leftBumper = new JoystickButton(this, 5);
        rightBumper = new JoystickButton(this, 6);

        leftJoyButton = new JoystickButton(this, 9);
        rightJoyButton = new JoystickButton(this, 10);

        leftTrigger = new FakeButton(this::isLeftTriggerPressed);
        rightTrigger = new FakeButton(this::isRightTriggerPressed);

        bumpers = new ButtonGroup(leftBumper, rightBumper);

        triggers = new ButtonGroup(leftTrigger, rightTrigger);
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

    /**
     * Checks if the left trigger is pressed down.
     * @return If the left trigger is pressed down.
     */
    public boolean isLeftTriggerPressed() {
        return Math.abs(getLeftTrigger()) > TRIGGER_PRESSED;
    }

    /**
     * Checks if the right trigger is pressed down.
     * @return If the right trigger is pressed down.
     */
    public boolean isRightTriggerPressed() {
        return Math.abs(getRightTrigger()) > TRIGGER_PRESSED;
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