package com.explodingbacon.bcnlib.quneo;

import com.explodingbacon.bcnlib.controllers.Joystick;
import com.explodingbacon.bcnlib.framework.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * An extension of Joystick meant to be used for the QuNeo MIDI Controller.
 *
 * @author Ryan Shavell
 * @version 2016.9.10
 */
public class QuNeoController extends Joystick {

    /**
     * Creates a QuNeoController.
     */
    public QuNeoController() {
        super();
    }


    @Override
    public double getX() {
        Log.w("Called QuNeoController.getX()! This function does not do anything.");
        return -2;
    }

    @Override
    public double getY() {
        Log.w("Called QuNeoController.getY()! This function does not do anything.");
        return -2;
    }

    @Override
    public double getZ() {
        Log.w("Called QuNeoController.getZ()! This function does not do anything.");
        return -2;
    }
}
