package com.explodingbacon.bcnlib.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * A class for a TouchSensor. //TODO: Change this class to a wrapper for DigitalInput?
 *
 * @author Ryan Shavell
 * @version 2016.2.6
 */

public class TouchSensor {

    private DigitalInput in;

    public TouchSensor(int channel) {
        in = new DigitalInput(channel);
    }

    public boolean get() {
        return in.get();
    }
}
