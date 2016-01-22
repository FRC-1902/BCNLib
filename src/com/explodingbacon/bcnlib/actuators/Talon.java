package com.explodingbacon.bcnlib.actuators;

public class Talon extends edu.wpi.first.wpilibj.Talon implements MotorInterface {

    int polarity = 1;

    public Talon(int channel) {
        super(channel);
    }

    @Override
    public void setPower(double power) {
        this.set(power * polarity);
    }

    @Override
    public double getPower() {
        return this.get();
    }

    @Override
    public void setReversed(boolean reversed) {
        polarity = reversed ? -1 : 1;
    }
}
