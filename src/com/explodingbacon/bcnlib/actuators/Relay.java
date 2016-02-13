package com.explodingbacon.bcnlib.actuators;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * An implementation of SpeedController for controlling a Relay.
 *
 * @author Ryan Shavell
 * @version 2016.2.13
 */

public class Relay implements SpeedController {

    private edu.wpi.first.wpilibj.Relay r;
    private double currentPower = 0;

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
                r.set(edu.wpi.first.wpilibj.Relay.Value.kForward);
                currentPower = 1;
            } else {
                r.set(edu.wpi.first.wpilibj.Relay.Value.kReverse);
                currentPower = -1;
            }

            r.set(edu.wpi.first.wpilibj.Relay.Value.kOn);
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

