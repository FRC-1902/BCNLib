package com.explodingbacon.bcnlib.framework;

/**
 * A PIDSource that is manually controlled by the code. Useful for PID loops that are not based off of sensor data.
 *
 * @author Ryan Shavell
 * @version 2016.2.4
 */

public class InternalSource implements PIDSource {

    private double distance;

    @Override
    public double getForPID() {
        return distance;
    }

    @Override
    public void reset() {}

    public void update(double d) {
        distance = d;
    }
}