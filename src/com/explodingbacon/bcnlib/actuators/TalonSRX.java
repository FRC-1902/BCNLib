package com.explodingbacon.bcnlib.actuators;

public class TalonSRX extends edu.wpi.first.wpilibj.TalonSRX implements MotorInterface {

    int polarity = 1;

    //Use this only for using a TalonSRX via PWM. If you want to control a SRX via CAN, use the CANTalon class
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
