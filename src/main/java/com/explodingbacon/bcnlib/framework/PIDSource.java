package com.explodingbacon.bcnlib.framework;

/**
 * An interface for PIDSources.
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */

public interface PIDSource {

    /**
     * Gets the value being used for PID.
     * @return The value being used for PID.
     */
    double getForPID();

    /**
     * Resets this PIDSource.
     */
    void reset();
}
