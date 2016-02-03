package com.explodingbacon.bcnlib.actuators;

/**
 * @author Ryan Shavell
 * @version 2016.1.23
 */

public class Relay extends Motor {

    private edu.wpi.first.wpilibj.Relay r;
    private double currentPower = 0;

    public Relay(int channel) {
        super();
        r = new edu.wpi.first.wpilibj.Relay(channel);
    }

    @Override
    public double getPower() {
        return currentPower;
    }

    @Override
    public void setPower(double power) {
        if (power == 0) {
            r.set(edu.wpi.first.wpilibj.Relay.Value.kOff);
            currentPower = 0;
        } else {
            if (reverse) power = -power;

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
}
