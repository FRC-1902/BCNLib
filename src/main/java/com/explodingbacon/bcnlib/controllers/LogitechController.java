package com.explodingbacon.bcnlib.controllers;

/**
 * An extension of GameController made specific for Logitech controllers.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class LogitechController extends GameController {

    public Button one, two, three, four;
    public Button start, select;
    public Button leftBumper, rightBumper;
    public Button leftJoyButton, rightJoyButton;
    public Button leftTrigger, rightTrigger;
    public ButtonGroup triggers;
    public ButtonGroup bumpers;

    /**
     * Makes a LogitechController on a port.
     * @param port The port.
     */
    public LogitechController(int port) {
        super(port);

        one = new JoystickButton(this, 1);
        two = new JoystickButton(this, 2);
        three = new JoystickButton(this, 3);
        four = new JoystickButton(this, 4);

        start = new JoystickButton(this, 9);
        select = new JoystickButton(this, 10);

        leftBumper = new JoystickButton(this, 5);
        rightBumper = new JoystickButton(this, 6);

        leftJoyButton = new JoystickButton(this, 11);
        rightJoyButton = new JoystickButton(this, 12);

        leftTrigger = new JoystickButton(this, 7);
        rightTrigger = new JoystickButton(this, 8);

        triggers = new ButtonGroup(leftTrigger, rightTrigger);
        bumpers = new ButtonGroup(leftBumper, rightBumper);
    }

    @Override
    public double getX2() {
        return getRawAxis(2);
    }

    @Override
    public double getY2() {
        return getRawAxis(3);
    }
}
