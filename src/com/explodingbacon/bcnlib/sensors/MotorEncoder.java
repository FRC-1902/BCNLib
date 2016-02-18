package com.explodingbacon.bcnlib.sensors;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * A class for Encoders that are wired into a TalonSRX (programmedly called a CANTalon)
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class MotorEncoder extends AbstractEncoder {

    private CANTalon tal;

    /**
     * Creates a MotorEncoder.
     * @param t The CANTalon this MotorEncoder is plugged into.
     */
    public MotorEncoder(CANTalon t) {
        tal = t;
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
}
