package com.explodingbacon.bcnlib.Utils;

import com.explodingbacon.bcnlib.Actuators.Motor;
import edu.wpi.first.wpilibj.Encoder;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 *          NOT FINISHED
 */
public class SingleMotorPositionPID {
    private Motor m;
    private Encoder e;
    private int kP, kI, kD;
    private int t = 0;

    /**
     * Creates a new <code>SingleMotorPositionPID</code> object with given values
     *
     * @param m  Motor to actuate.
     * @param e  Encoder to read.
     * @param kP Proportional tuning variable. Set to 0 to disable the P term.
     * @param kI Integral tuning variable. Set to 0 to disable to I term.
     * @param kD Derivative tuning variable. Set to 0 to disable the D term.
     */
    public SingleMotorPositionPID(Motor m, Encoder e, int kP, int kI, int kD) {
        this.m = m;
        this.e = e;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    /**
     * Set a new position target.
     *
     * @param target Position target in encoder clicks from encoder zero
     */
    public void setPosition(int target) {
        t = target;
    }

    /**
     * Get the currently set target.
     *
     * @return Position target in encoder clicks from encoder zero
     */
    public int getPosition() {
        return t;
    }

    /**
     * Reset the encoder to zero.
     * <p>
     * NOTE: This will reset the encoder object <b>EVERYWHERE</b> it is used.
     */
    public void resetPosition() {
        e.reset();
    }
}
