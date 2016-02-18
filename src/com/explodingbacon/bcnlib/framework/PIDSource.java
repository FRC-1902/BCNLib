package com.explodingbacon.bcnlib.framework;

/**
 * An interface for PIDSources.
 *
 * @author Dominic Canora
 * @version 2016.2.17
 */

public interface PIDSource {

    /**
     * Gets the value being used for PID.
     * @return The value being used for PID.
     */
    double getForPID();

    /**
     * Resets this PIDSource. TODO: Either use this or delete this.
     */
    void reset();
}
