package com.explodingbacon.bcnlib.sensors;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * A class for Encoders that are wired into a TalonSRX (programmedly called a WPI_TalonSRX)
 *
 * @author Ryan Shavell
 * @version 2017.1.8
 */

public class MotorEncoder extends AbstractEncoder {

    private WPI_TalonSRX tal;

    /**
     * Creates a MotorEncoder.
     *
     * @param t The WPI_TalonSRX this MotorEncoder is plugged into.
     */
    public MotorEncoder(WPI_TalonSRX t) {
        tal = t;
    }

    @Override
    public void reset() {
        //tal.setEncPosition(0); //FIXME: Encoder values for WPI_TalonSRX
    }

    @Override
    public int get() {
        int sign = isReversed() ? -1 : 1;
        //return sign * tal.getEncPosition(); //FIXME: Encoder values for WPI_TalonSRX
        return 0;
    }

    @Override
    public double getRate() {
        double sign = isReversed() ? -1 : 1;
        //return sign * tal.getEncVelocity();
        //return sign * tal.getSpeed(); //FIXME: Encoder values for WPI_TalonSRX
        return 0;
    }
}
