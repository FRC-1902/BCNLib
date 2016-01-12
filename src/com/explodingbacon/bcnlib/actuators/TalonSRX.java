package com.explodingbacon.bcnlib.actuators;

public class TalonSRX extends edu.wpi.first.wpilibj.TalonSRX implements com.explodingbacon.bcnlib.actuators.Motor {

    int polarity = 1;

    public TalonSRX(int channel) {
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
