package com.explodingbacon.bcnlib.actuators;

public class SD540 extends edu.wpi.first.wpilibj.SD540 implements MotorInterface {

    int polarity = 1;

    public SD540(int channel) {
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