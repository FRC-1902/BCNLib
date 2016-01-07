package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.actuators.Motor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.HLUsageReporting;

/**
 * Extendable class that allows us to reference all PID use cases the same way, and allows us to write some code only once
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public abstract class PIDController implements Runnable { //TODO: Check this
    private Motor m;
    private Encoder e;
    private int kP, kI, kD, mode;
    private double p, i, d, lastP = 0, min, max;
    private int t = 0;
    private Thread thread;
    private boolean enabled;

    public static class Mode {
        static int POSITION = 9277;
        static int RATE = 10650;
    }


    /**
     * Creates a new <code>PIDController</code> object with given values, using the defaults for min and max
     * motor setpoint.
     *
     * @param m  Motor to actuate.
     * @param e  Encoder to read.
     * @param kP Proportional tuning variable. Set to 0 to disable the P term.
     * @param kI Integral tuning variable. Set to 0 to disable to I term.
     * @param kD Derivative tuning variable. Set to 0 to disable the D term.
     */
    public PIDController(Motor m, Encoder e, int mode, int kP, int kI, int kD) {
        this.m = m;
        this.e = e;
        this.mode = mode;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.min = 0.2;
        this.max = 0.8;
        this.thread = new Thread(this);
    }


    /**
     * Creates a new <code>PIDController</code> object with given values.
     *
     * @param m   Motor to actuate.
     * @param e   Encoder to read.
     * @param kP  Proportional tuning variable. Set to 0 to disable the P term.
     * @param kI  Integral tuning variable. Set to 0 to disable to I term.
     * @param kD  Derivative tuning variable. Set to 0 to disable the D term.
     * @param min The minimum setpoint to the motor. Values below this will be scaled down to 0.
     * @param max The maximum setpoint to the motor. Values above this will be scaled to this value.
     */
    public PIDController(Motor m, Encoder e, int mode, int kP, int kI, int kD, double min, double max) {
        this.m = m;
        this.e = e;
        this.mode = mode;
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
    public void setTarget(int target) {
        t = target;
    }

    /**
     * Get the currently set target.
     *
     * @return Position target in encoder clicks from encoder zero
     */
    public int getTarget() {
        return t;
    }

    /**
     * Get the difference between the setpoint and the current position, in encoder clicks.
     *
     * @return The current error, in encoder clicks
     */
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

    private double getCurrent() {
        if (mode == Mode.POSITION) {
            return e.getRaw();
        } else if (mode == Mode.RATE) {
            return e.getRate();
        } else {
            return 0;
        }
    }

    @Override
    public void run() {
        p = t - getCurrent();
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
