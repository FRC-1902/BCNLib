package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.framework.Button;
import com.explodingbacon.bcnlib.framework.Joystick;
import com.explodingbacon.bcnlib.framework.JoystickButton;

/**
 * An extension class of Joystick that makes it much easier to do XboxController-specific things, such as reading from the DPad or rumbling.
 *
 * @author Ryan Shavell
 * @version 2016.2.2
 */

public abstract class GameController extends Joystick {

    public Timer rumbleTimer;

    public GameController(int port) {
        super(port);
    }

    /**
     * Gets the X axis of the second joystick.
     * @return The X axis of the right XboxController joystick.
     */
    public double getX2() { return -2; }

    /**
     * Gets the Y axis of the second joystick.
     * @return The Y axis of the second joystick.
     */
    public double getY2() {
        return -2;
    }

    /**
     * Gives the current Direction of the DPad.
     * @return The Direction of the DPad. Returns null if the DPad is not pressed.
     */
    public Direction getDPad() {
        return Direction.toDirection(getPOV(0));
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
}