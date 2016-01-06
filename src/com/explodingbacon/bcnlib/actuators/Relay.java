package com.explodingbacon.bcnlib.actuators;

public class Relay extends edu.wpi.first.wpilibj.Relay implements com.explodingbacon.bcnlib.actuators.Motor {

    boolean reversed = false;
    double currentPower = 0;

    public Relay(int channel) {
        super(channel);
    }

    @Override
    public void setPower(double power) {
        if (power == 0) {
            set(Value.kOff);
            currentPower = 0;
        } else {
            if (reversed) power = -power;

            if (power > 0) {
                set(Value.kForward);
                currentPower = 1;
            } else {
                set(Value.kReverse);
                currentPower = -1;
            }

            set(Value.kOn);
        }
    }

    @Override
    public double getPower() {
        return currentPower;
    }

    @Override
    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }
}
