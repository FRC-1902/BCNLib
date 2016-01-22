package com.explodingbacon.bcnlib.actuators;

public class VictorSP extends edu.wpi.first.wpilibj.VictorSP implements MotorInterface {

    int polarity = 1;

    public VictorSP(int channel) {
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
