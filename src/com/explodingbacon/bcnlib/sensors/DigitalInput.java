package com.explodingbacon.bcnlib.sensors;

/**
 * A class for a DigitalInput.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
 */

public class DigitalInput {

    private edu.wpi.first.wpilibj.DigitalInput in;

    /**
     * Creates a DigitalInput.
     *
     * @param channel The channel this DigitalInput is on.
     */
    public DigitalInput(int channel) {
        in = new edu.wpi.first.wpilibj.DigitalInput(channel);
    }

    /**
     * Gets the value of this DigitalInput.
     *
     * @return The value of this DigitalInput.
     */
    public boolean get() {
        return in.get();
    }
}
