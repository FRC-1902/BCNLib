package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.actuators.Motor;
import edu.wpi.first.wpilibj.Encoder;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 *          NOT FINISHED
 */
public class SingleMotorPositionPID implements Runnable {
    private Motor m;
    private Encoder e;
    private int kP, kI, kD;
    private double p, i, d, lastP = 0, min, max;
    private int t = 0;
    private Thread thread;
    private boolean enabled;

    /**
     * Creates a new <code>SingleMotorPositionPID</code> object with given values, using the defaults for min and max
     * motor setpoint.
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
        this.min = 0.2;
        this.max = 0.8;
        this.thread = new Thread(this);
    }

    /**
     * Creates a new <code>SingleMotorPositionPID</code> object with given values.
     *
     * @param m   Motor to actuate.
     * @param e   Encoder to read.
     * @param kP  Proportional tuning variable. Set to 0 to disable the P term.
     * @param kI  Integral tuning variable. Set to 0 to disable to I term.
     * @param kD  Derivative tuning variable. Set to 0 to disable the D term.
     * @param min The minimum setpoint to the motor. Values below this will be scaled down to 0.
     * @param max The maximum setpoint to the motor. Values above this will be scaled to this value.
     */
    public SingleMotorPositionPID(Motor m, Encoder e, int kP, int kI, int kD, double min, double max) {
        this.m = m;
        this.e = e;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.min = min;
        this.max = max;
        this.thread = new Thread(this);
    }

    /**
     * Enables control of the motor.
     */
    public void enable() {
        enabled = true;
    }

    /**
     * Disables control of the motor.
     */
    public void disable() {
        enabled = false;
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

    public int getCurrentError() {
        return t - e.getRaw();
    }

    /**
     * Reset the encoder to zero.
     * <p>
     * NOTE: This will reset the encoder object <b>EVERYWHERE</b> it is used.
     */
    public void resetPosition() {
        e.reset();
    }

    @Override
    public void run() {
        p = t - e.getRaw();
        i += p;
        d = lastP - p;
        lastP = p;

        double setpoint = p * kP + i * kI + d * kD;
        setpoint = Utils.minMax(setpoint, 0.1, 1);

        if (enabled)
            m.setPower(setpoint);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e1) {
            assert false; //Not exactly sure what is proper to do here, I found this on StackExchange
        }
    }
}
