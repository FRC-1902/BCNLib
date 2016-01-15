package com.explodingbacon.bcnlib.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan Shavell
 * @version 2016.1.14
 */

public class NetGamepad {

    private List<NetJoystick> joys = new ArrayList<>();
    private List<NetButton> buttons = new ArrayList<>();

    public NetGamepad(String key, int joyNum, int buttonNum) {
        for (int i=1; i<=joyNum; i++) {
            joys.add(new NetJoystick(key + "_joy_" + i));
        }
        for (int i=1; i<=buttonNum; i++) {
            buttons.add(new NetButton(key + "_button_" + i));
        }
    }

    public NetJoystick getJoystick(int index) { return joys.get(index); }

    public NetButton getButton(int index) { return buttons.get(index); }
}
