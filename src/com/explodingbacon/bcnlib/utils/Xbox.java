package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.framework.Button;
import com.explodingbacon.bcnlib.framework.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

/**
 * An extension class of Joystick that makes it much easier to do Xbox-specific things, such as reading from the DPad or rumbling.
 *
 * @author Ryan Shavell
 * @version 2016.1.20
 */

public class Xbox extends Joystick {

    public Button a;
    public Button b;
    public Button x;
    public Button y;
    public Button start;
    public Button select;
    public Button leftBumper;
    public Button rightBumper;
    public Button leftJoyButton;
    public Button rightJoyButton;
    public Timer rumbleTimer;

    public Xbox(int port) {
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
     * Gets the X axis of the right Xbox joystick.
     * @return The X axis of the right Xbox joystick.
     */
    public double getX2() {
        return getRawAxis(4);
    }

    /**
     * Gets the Y axis of the right Xbox joystick.
     * @return The Y axis of the right Xbox joystick.
     */
    public double getY2() {
        return getRawAxis(5);
    }

    /**
     * Gives the current Direction of the DPad.
     * @return The Direction of the DPad. Returns null if the DPad is not pressed.
     */
    public Direction getDPad() {
        return Direction.toDirection(getPOV(0));
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
     * Makes the controller rumble.
     * @param l The left rumble value.
     * @param r The right rumble value.
     */
    public void rumble(float l, float r) {
        setRumble(RumbleType.kLeftRumble, l);
        setRumble(RumbleType.kRightRumble, r);
    }

    /**
     * Makes the controller rumble for X seconds.
     * @param l The left rumble value.
     * @param r The right rumble value.
     * @param seconds How long the controller should rumble.
     */
    public void rumble(float l, float r, double seconds) {
        rumble(l, r);
        rumbleTimer = new Timer(seconds, false, new TimerUser() {
            public void timer() {
                rumble(0, 0);
            }
            public void timerStop() {
                rumbleTimer = null;
            }
        }).start();
    }

    public enum Direction {
        UP(0),
        UP_RIGHT(45),
        RIGHT(90),
        DOWN_RIGHT(135),
        DOWN(180),
        DOWN_LEFT(225),
        LEFT(270),
        UP_LEFT(315),

        NONE(-1);

        public static Direction[] allDirections = new Direction[]{Direction.UP, Direction.UP_RIGHT, Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN, Direction.DOWN_LEFT, Direction.LEFT, Direction.UP_LEFT, Direction.NONE};
        public int angle;

        Direction(int angle) {
            this.angle = angle;
        }

        public boolean isUp() {
            return (this == Direction.UP_LEFT || this == Direction.UP || this == Direction.UP_RIGHT);
        }

        public boolean isRight() {
            return (this == Direction.UP_RIGHT || this == Direction.RIGHT || this == Direction.DOWN_RIGHT);
        }

        public boolean isDown() {
            return (this == Direction.DOWN_LEFT || this == Direction.DOWN || this == Direction.DOWN_RIGHT);
        }

        public boolean isLeft() {
            return (this == Direction.UP_LEFT || this == Direction.LEFT || this == Direction.DOWN_LEFT);
        }

        public static Direction toDirection(int angle) {
            for (Direction d : allDirections) {
                if (d.angle == angle) {
                    return d;
                }
            }
            return Direction.NONE;
        }
    }
}