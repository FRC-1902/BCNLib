package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.PIDSource;
import edu.wpi.first.wpilibj.CANTalon;

/**
 * A class for Encoders that are wired into a TalonSRX (programmedly called a CANTalon)
 *
 * @author Ryan Shavell
 * @version 2016.2.6
 */

public class MotorEncoder implements EncoderInterface, PIDSource {

    private CANTalon tal;
    private byte pidMode;

    public MotorEncoder(CANTalon t) {
        tal = t;
    }

    public void setPidMode(byte mode) {
        pidMode = mode;
    }

    @Override
    public void reset() {
        tal.reset();
    }

    @Override
    public int get() {
        return tal.getEncPosition();
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
}
