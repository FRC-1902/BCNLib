package com.explodingbacon.bcnlib.networking;

import com.explodingbacon.bcnlib.controllers.FakeButton;
import com.explodingbacon.bcnlib.framework.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Button that is updated over TCP when a specific button on a keyboard is pressed.
 *
 * @author Ryan Shavell
 * @version 2016.9.10
 */
public class KeyboardButton extends FakeButton {

    private static List<KeyboardButton> keyboardButtons = new ArrayList<>();

    protected int keycode;

    /**
     * Creates a KeyboardButton.
     *
     * @param keyc The keycode for the key this KeyboardButton will listen to.
     */
    public KeyboardButton(int keyc) {
        super();
        keycode = keyc;
        boolean taken = false;
        for (KeyboardButton b : keyboardButtons) {
            if (b.keycode == keycode) {
                taken = true;
                break;
            }
        }
        if (!taken) {
            keyboardButtons.add(this);
        } else {
            Log.w("Created a duplicate KeyboardButton for keycode \"" + keycode + "\".");
        }
    }

    /**
     * Gets the KeyboardButton for a certain key.
     * @param key The keycode for the key the KeyboardButton listens to.
     * @return The KeyboardButton.
     */
    public static KeyboardButton getButton(int key) {
        for (KeyboardButton b : keyboardButtons) {
            if (b.keycode == key) {
                return b;
            }
        }
        return null;
    }
}
