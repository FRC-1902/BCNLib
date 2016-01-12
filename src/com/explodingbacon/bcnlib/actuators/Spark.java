package com.explodingbacon.bcnlib.actuators;

public class Spark extends edu.wpi.first.wpilibj.Spark implements com.explodingbacon.bcnlib.actuators.Motor {

    int polarity = 1;

    public Spark(int channel) {
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