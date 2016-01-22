package com.explodingbacon.bcnlib.actuators;

public class CANTalon extends edu.wpi.first.wpilibj.CANTalon implements MotorInterface {

    int polarity = 1;

    //Use this only for using a TalonSRX via CAN. If you want to control a SRX via PWM, use the TalonSRX class
    public CANTalon(int channel) {
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
