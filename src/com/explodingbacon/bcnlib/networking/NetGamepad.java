package com.explodingbacon.bcnlib.networking;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for NetGamepads, which contain NetJoysticks and NetButtons. TODO: Delete?
 *
 * @author Ryan Shavell
 * @version 2016.1.26
 */

public class NetGamepad {

    private List<NetJoystick> joys = new ArrayList<>();
    private List<NetButton> buttons = new ArrayList<>();

    public NetGamepad(String key, int joyNum, int buttonNum) {
        for (int i = 1; i <= joyNum; i++) {
            joys.add(new NetJoystick(key + "_joy_" + i));
        }
        for (int i = 1; i <= buttonNum; i++) {
            buttons.add(new NetButton(key + "_button_" + i));
        }
    }

    /**
     * Checks if the NetButton at buttonIndex is pressed.
     *
     * @param buttonIndex The index of the NetButton.
     * @return If the NetButton at buttonIndex is pressed.
     */
    public boolean isButtonPressed(int buttonIndex) {
        return getButton(buttonIndex).get();
    }

    /**
     * Gets the X axis value of the NetJoystick at joystickIndex.
     *
     * @param joystickIndex The index of the NetJoystick.
     * @return The X axis value of the NetJoystick at joystickIndex.
     */
    public double getJoystickX(int joystickIndex) {
        return getJoystick(joystickIndex).getX();
    }

    /**
     * Gets the Y axis value of the NetJoystick at joystickIndex.
     *
     * @param joystickIndex The index of the NetJoystick.
     * @return The Y axis value of the NetJoystick at joystickIndex.
     */
    public double getJoystickY(int joystickIndex) {
        return getJoystick(joystickIndex).getY();
    }

    /**
     * Gets the NetJoystick at index.
     *
     * @param index The index of the NetJoystick.
     * @return The NetJoystick at index.
     */
    public NetJoystick getJoystick(int index) {
        return joys.get(index);
    }

    /**
     * Gets the NetButton at index.
     *
     * @param index The index of the NetButton
     * @return The NetButton.
     */
    public NetButton getButton(int index) {
        return buttons.get(index);
    }
}
