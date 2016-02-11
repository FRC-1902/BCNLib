package com.explodingbacon.bcnlib.actuators;

/**
 * An implementation of SolenoidInterface that controls a Solenoid via a Raspberry Pi.
 *
 * @author Ryan Shavell
 * @version 2016.2.10
 */

public class DoubleSolenoid implements SolenoidInterface {

    private DoubleSolenoid sol;

    public DoubleSolenoid(int channel1, int channel2) {
        sol = new DoubleSolenoid(channel1, channel2);
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