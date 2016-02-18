package com.explodingbacon.bcnlib.actuators;

/**
 * An implementation of SolenoidInterface that controls a Solenoid.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class Solenoid implements SolenoidInterface {

    private edu.wpi.first.wpilibj.Solenoid sol;

    /**
     * Create a solenoid on the given channel.
     * @param channel The channel where this Solenoid is plugged in
     */
    public Solenoid(int channel) {
        sol = new edu.wpi.first.wpilibj.Solenoid(channel);
    }

    @Override
    public boolean get() {
        return sol.get();
    }

    @Override
    public void set(boolean state) {
        sol.set(state);
    }
}