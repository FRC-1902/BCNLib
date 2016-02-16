package com.explodingbacon.bcnlib.sensors;

/**
 * A class for a DigitalInput.
 *
 * @author Ryan Shavell
 * @version 2016.2.15
 */

public class DigitalInput {

    private edu.wpi.first.wpilibj.DigitalInput in;

    public DigitalInput(int channel) {
        in = new edu.wpi.first.wpilibj.DigitalInput(channel);
    }

    public boolean get() {
        return in.get();
    }
}
