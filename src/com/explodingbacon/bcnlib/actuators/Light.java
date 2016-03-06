package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.utils.Timer;

/**
 * A utility class for controlling a light connected to the PCM.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
 */
public class Light {

    private SolenoidInterface sol;
    private Timer t = null;
    private boolean active = false;

    /**
     * Creates a Light with an existing Solenoid object
     *
     * @param s The port which the light is plugged in.
     */
    public Light(SolenoidInterface s) {
        sol = s;
    }

    /**
     * Creates a Light on the given port
     *
     * @param port The port on the PCM where the light is plugged in.
     */
    public Light(int port) {
        sol = new Solenoid(port);
    }

    /**
     * Checks if this light is doing anything.
     *
     * @return If this light is doing anything.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Turns off this Light and disables any activities it was doing.
     */
    public void stop() {
        t = null;
        active = false;
        set(false);
    }

    /**
     * Turns on this Light.
     */
    public void enable() {
        set(true);
        active = true;
    }

    /**
     * Makes this Light blink on and off every X amount of seconds. On for X amount, off for X amount.
     *
     * @param sec How many seconds this Light should spend on and off each.
     */
    public void blink(double sec) {
        set(true);
        active = true;
        t = new Timer(sec, () -> set(!get()));
        t.start();
    }

    /**
     * Sets the state of this Light.
     *
     * @param b The state of this Light.
     */
    private void set(boolean b) {
        sol.set(b);
    }

    /**
     * Gets the state of this Light.
     *
     * @return The state of this Light.
     */
    private boolean get() {
        return sol.get();
    }
}
