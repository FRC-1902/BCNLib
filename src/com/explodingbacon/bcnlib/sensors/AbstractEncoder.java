package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.PIDSource;

/**
 * A abstract class for Encoders.
 *
 * @author Ryan Shavell
 * @version 2016.2.15
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

    @Override
    public double getForPID() {
        if (getPIDMode() == PIDMode.POSITION) return get();
        if (getPIDMode() == PIDMode.RATE) return getRate();
        return 0;
    }

    public abstract void reset();

    public abstract double getRate();

    public abstract int get();

    public enum PIDMode {
        RATE,
        POSITION
    }
}
