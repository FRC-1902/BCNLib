package com.explodingbacon.bcnlib.actuators;

/**
 * An interface that lets us talk to all motors the same way, regardless of what motor controller is used.
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public interface Motor {
    /**
     * Set a motor's power
     * @param power Value between 1 and -1 where -1 is full reverse, 1 is full forward, and 0 is full forward.
     */
    void setPower(double power);

    /**
     * Get a motors set power
     * @return The setpoint for the motor
     */
    double getPower();

    /**
     * Set a motor to negate all values sent to it.
     *
     * @param reversed True to reverse the motor's direction
     */
    void setReversed(boolean reversed);
}
