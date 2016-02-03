package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.CANTalon;

public class MotorEncoder implements EncoderInterface {

    private CANTalon tal;

    public MotorEncoder(CANTalon t) {
        t = tal;
    }

    @Override
    public void reset() {
        tal.reset();
    }

    @Override
    public double getRate() {
        return tal.getEncVelocity();
    }

    @Override
    public int get() {
        return tal.getEncPosition();
    }
}
