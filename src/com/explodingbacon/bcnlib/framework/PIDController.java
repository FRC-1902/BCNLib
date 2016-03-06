package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.actuators.FakeMotor;
import com.explodingbacon.bcnlib.actuators.Motor;
import com.explodingbacon.bcnlib.sensors.AbstractEncoder;
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
    private double t = 0;
    private double tolerance = 1000;
    private Thread thread;
    private Runnable whenFinished = null;
    private Runnable extraCode = null;
    private boolean enabled = false, inverted = false, done = false;


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
    public PIDController(Motor m, PIDSource s, double kP, double kI, double kD) {
        this.m = (m != null) ? m : new FakeMotor();
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
    public PIDController(Motor m, PIDSource s, double kP, double kI, double kD, double min, double max) {
        this.m = (m != null) ? m : new FakeMotor();
        this.s = s;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.min = min;
        this.max = max;
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Resets the P, I, D, and lastP variables.
     */
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
        done = false;
    }

    /**
     * Disables controllers of the motor. This also stops the motor
     */
    public void disable() {
        enabled = false;
        m.setPower(0);
    }

    /**
     * Checks if this PIDController is enabled.
     * @return If this PIDController is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set a new position target.
     *
     * @param target Position target in encoder clicks from encoder zero
     */
    public void setTarget(double target) {
        t = target;
        done = false;
    }

    /**
     * Get the currently set target.
     *
     * @return Position target in encoder clicks from encoder zero
     */
    public double getTarget() {
        return t;
    }

    /**
     * Set the PIDController to invert its input
     *
     * @param inverted Whether the PIDController should invert its input
     * @return This PIDController (for method chaining)
     */
    public PIDController setInputInverted(Boolean inverted) {
        this.inverted = inverted;
        return this;
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
     * Get the power that is currently being sent to the motor.
     *
     * @return The power sent to the motor
     */
    public double getMotorPower() {
        return m.getPower();
    }

    /**
     * Gets the current value of the PID source.
     * @return The current value of the PID source.
     */
    public double getCurrentSourceValue() {
        return s.getForPID();
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

    /**
     * Set the tolerance for determining if the loop is finished. Default is 1000
     * @param tolerance The tolerance, in whatever units the PIDSource provides
     */
    public void setFinishedTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * Checks if this PIDController is done.
     * @return If this PIDController is done.
     */
    public boolean isDone() {
        return done;
    }


    /**
     * Makes the Thread wait until this PIDController has reached its destination.
     *
     * @return True.
     */
    public boolean waitUntilDone() { //TODO: Make sure that you can reliably call this then disable the PIDController and not be cutting the PID loop short
        return Utils.waitFor(this::isDone);
    }

    /**
     * Makes the Thread wait until this PIDController has reached its destination, or the timeout seconds are reached.
     * @param timeout How many seconds to wait before breaking out of the loop regardless of the PID loop's status.
     * @return True if the wait completed normally, false is it was completed due to reaching the timeout.
     */
    public boolean waitUntilDone(double timeout) {
        return Utils.waitFor(this::isDone, timeout);
    }

    /**
     * Defines code that runs when this PID loop is done.
     * @param r The code to be run.
     * @return This PIDController.
     */
    public PIDController whenFinished(Runnable r) {
        whenFinished = r;
        return this;
    }

    /**
     * Sets the code that is run every loop of the PID.
     * @param r The code to run.
     */
    public void setExtraCode(Runnable r) {
        extraCode = r;
    }

    public void log() {
        if(enabled)
            Log.t("Target: " + getTarget() + "; Current Value: " + getCurrentSourceValue() + "; Motor Setpoint: " + getMotorPower());
    }

    @Override
    public void run() {
        boolean isRate = false;
        if (s instanceof AbstractEncoder) {
            if (((AbstractEncoder) s).getPIDMode() == AbstractEncoder.PIDMode.RATE) {
                isRate = true;
            }
        }
        while (true) {
            if (enabled && RobotCore.isEnabled()) {
                p = t - s.getForPID();

                if (inverted) p *= -1;

                i += p;
                d = lastP - p;
                lastP = p;

                i = Math.abs(i * kI) > 1 ? (1 / kI) : i; //Prevent i windup

                double setpoint = p * kP + i * kI - d * kD;
                setpoint = Utils.minMax(setpoint, 0.1, 1);

                double power = Utils.minMax(setpoint, min, max);

                //If our target is 0 and our target is a rate, set the motor to 0, since that'll get us a rate of 0 easily
                if (isRate && getTarget()== 0) {
                    power = 0;
                    p = 0;
                    i = 0;
                    d = 0;
                    lastP = 0;
                }

                if(Utils.sign(power) != Utils.sign(p)) {
                    power = 0;
                }

                m.setPower(power);

                try {
                    if (extraCode != null) {
                        Utils.runInOwnThread(extraCode);
                    }
                } catch (Exception e) {
                    Log.e("PIDController.extraCode Runnable error!");
                    e.printStackTrace();
                }

                done = Math.abs(p) <= tolerance;

                if (done && whenFinished != null) {
                    try {
                        whenFinished.run();
                    } catch (Exception e) {
                        Log.e("PIDController.whenFinished Runnable error!");
                        e.printStackTrace();
                    }
                }
            } else {
                done = true;
                disable();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
                break;
            }
        }
    }
}
