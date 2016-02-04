package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.PIDSource;
import edu.wpi.first.wpilibj.CANTalon;

public class MotorEncoder implements EncoderInterface, PIDSource {

    private CANTalon tal;
    private byte pidMode;

    public MotorEncoder(CANTalon t) {
        this.tal = t;
    }

    public void setPidMode(byte mode) {
        this.pidMode = mode;
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
    public double getForPID() {
        if (pidMode == EncoderInterface.POSITION) return get();
        if (pidMode == EncoderInterface.RATE) return getRate();
        return 0;
    }

    @Override
    public int get() {
        return tal.getEncPosition();
    }
}
