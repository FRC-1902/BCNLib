package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.actuators.Motor;
import com.explodingbacon.bcnlib.utils.Utils;

/**
 * All-encompassing PID controller that can be used for rate and for position controllers. Set any tuning parameter to 0 to
 * disable that parameter.
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public class PIDController implements Runnable { //TODO: Check this
    private Motor m;
    private PIDSource s;
    private double kP, kI, kD;
    private double p, i, d, lastP = 0, min, max;
    private int t = 0;
    private Thread thread;
    private boolean enabled = false;


    /**
     * Creates a new <code>PIDController</code> object with given values, using the defaults for min and max
     * motor target.
     *
     * @param m  Motor to actuate.
     * @param s  Encoder to read.
     * @param kP Proportional tuning variable. Set to 0 to disable the P term.
     * @param kI Integral tuning variable. Set to 0 to disable to I term.
     * @param kD Derivative tuning variable. Set to 0 to disable the D term.
     */
    public PIDController(Motor m, PIDSource s, int kP, int kI, int kD) {
        this.m = m;
        this.s = s;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.min = 0.2;
        this.max = 0.8;
        this.thread = new Thread(this);
        this.thread.start();
    }


    /**
     * Creates a new <code>PIDController</code> object with given values.
     *
     * @param m   Motor to actuate.
     * @param s   Encoder to read.
     * @param kP  Proportional tuning variable. Set to 0 to disable the P term.
     * @param kI  Integral tuning variable. Set to 0 to disable to I term.
     * @param kD  Derivative tuning variable. Set to 0 to disable the D term.
     * @param min The minimum target to the motor. Values below this will be scaled down to 0.
     * @param max The maximum target to the motor. Values above this will be scaled to this value.
     */
    public PIDController(Motor m, PIDSource s, int kP, int kI, int kD, double min, double max) {
        this.m = m;
        this.s = s;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.min = min;
        this.max = max;
        this.thread = new Thread(this);
        this.thread.start();
    }

    private void reset() {
        this.disable();
        this.p = 0;
        this.i = 0;
        this.d = 0;
        this.lastP = 0;
    }

    /**
     * Enables controllers of the motor.
     */
    public void enable() {
        reset();
        enabled = true;
    }

    /**
     * Disables controllers of the motor. This also stops the motor
     */
    public void disable() {
        enabled = false;
        m.setPower(0);
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
     * Get the difference between the target and the current position, in encoder clicks.
     *
     * @return The current error, in encoder clicks
     */
    public double getCurrentError() {
        return t - s.getForPID();
    }

    /**
     * Reset the encoder to zero.
     * <p>
     * NOTE: This will reset the encoder object <b>EVERYWHERE</b> it is used.
     */
    public void resetSource() {
        s.reset();
    }

    /**
     * On-the-fly re-tuning of the onLoop. Be careful here, there shouldn't be any reason to use this except to tune.
     * For safety, this disables and resets the <code>PIDController</code>
     *
     * @param kP  Proportional tuning variable. Set to 0 to disable the P term.
     * @param kI  Integral tuning variable. Set to 0 to disable to I term.
     * @param kD  Derivative tuning variable. Set to 0 to disable the D term.
     */
    public void reTune(double kP, double kI, double kD) {
        //TODO: Throw RuntimeException if robot is not in test mode
        reset();
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    @Override
    public void run() {
        p = t - s.getForPID();
        i += p;
        d = lastP - p;
        lastP = p;

        double setpoint = p * kP + i * kI + d * kD;
        setpoint = Utils.minMax(setpoint, 0.1, 1);

        if (enabled)
            m.setPower(Utils.minMax(setpoint, min, max));

        try {
            Thread.sleep(10);
        } catch (InterruptedException e1) {
            assert false; //Not exactly sure what is proper to do here, I found this on StackExchange
        }
    }
}
