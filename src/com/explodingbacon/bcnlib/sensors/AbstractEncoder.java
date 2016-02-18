package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.PIDSource;

/**
 * A abstract class for Encoders.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public abstract class AbstractEncoder implements PIDSource {
    private PIDMode mode = PIDMode.POSITION;

    /**
     * Sets the PID Mode of this Encoder.
     * @param p The PID mode.
     */
    public void setPIDMode(PIDMode p) {
        mode = p;
    }

    /**
     * Gets the PID Mode of this Encoder.
     * @return The PID Mode of this Encoder.
     */
    public PIDMode getPIDMode() {
        return mode;
    }

    /**
     * Gets the rate of this Encoder.
     * @return The rate of this Encoder.
     */
    public abstract double getRate();

    /**
     * Gets the value of this Encoder.
     * @return The value of this Encoder.
     */
    public abstract int get();

    @Override
    public double getForPID() {
        if (getPIDMode() == PIDMode.POSITION) return get();
        if (getPIDMode() == PIDMode.RATE) return getRate();
        return 0;
    }

    @Override
    public abstract void reset();

    public enum PIDMode {
        RATE,
        POSITION
    }
}
