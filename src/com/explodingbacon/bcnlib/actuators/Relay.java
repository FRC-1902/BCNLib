package com.explodingbacon.bcnlib.actuators;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * An implementation of SpeedController for controlling a Relay.
 *
 * @author Ryan Shavell
 * @version 2016.2.19
 */

public class Relay implements SpeedController {

    private edu.wpi.first.wpilibj.Relay r;
    private double currentPower = 0;

    /**
     * Create a new Relay on the given channel
     * @param channel The channel that this Relay is plugged in
     */
    public Relay(int channel) {
        super();
        r = new edu.wpi.first.wpilibj.Relay(channel);
    }

    @Override
    public double get() {
        return currentPower;
    }

    @Override
    public void set(double speed, byte syncGroup) {
        set(speed);
    }

    @Override
    public void set(double power) {
        if (power == 0) {
            r.set(edu.wpi.first.wpilibj.Relay.Value.kOff);
            currentPower = 0;
        } else {
            if (power > 0) {
                r.setDirection(edu.wpi.first.wpilibj.Relay.Direction.kForward);
                r.set(edu.wpi.first.wpilibj.Relay.Value.kOn);
                currentPower = 1;
            } else {
                r.setDirection(edu.wpi.first.wpilibj.Relay.Direction.kForward);
                r.set(edu.wpi.first.wpilibj.Relay.Value.kOn);
                currentPower = -1;
            }
        }
    }

    @Override
    public void setInverted(boolean isInverted) {} //Not implemented due to not being needed by Motor.java

    @Override
    public boolean getInverted() {
        return false;
    }

    @Override
    public void disable() {} //Not implemented due to not being needed by Motor.java

    @Override
    public void stopMotor() {
        r.stopMotor();
    }

    @Override
    public void pidWrite(double output) {} //Not implemented due to not being needed by Motor.java
}

