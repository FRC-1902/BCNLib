package com.explodingbacon.bcnlib.actuators;

/**
 * An interface to unify talking to Solenoids
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */
public interface SolenoidInterface {

    /**
     * Get whether this Solenoid is active
     * @return Whether this Solenoid is active
     */
    boolean get();

    /**
     * Sets whether this Solenoid should be active
     * @param state Whether this Solenoid should be active
     */
    void set(boolean state);

}
