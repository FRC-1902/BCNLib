package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.actuators.FakeMotor;
import com.explodingbacon.bcnlib.sensors.AbstractEncoder;
import com.explodingbacon.bcnlib.utils.Utils;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * All-encompassing PID controller that can be used for rate and for position controllers. Set any tuning parameter to 0 to
 * disable that parameter.
 *
 * @author Dominic Canora
 * @version 2017.1.26
 */
public class PIDController implements Runnable {
    private SpeedController m;
    private PIDSource s;
    private double kP, kI, kD;
    private double p, i, d, lastP = 0, min, max, startingSign;
    private double t = 0;
    private double tolerance = 1000;
    private double power;
    private Thread thread;
    private Runnable whenFinished = null;
    private Runnable extraCode = null;
    private static final Object verboseLogLock = new Object(), setLock = new Object();
    private boolean enabled = false, inverted = false, done = false, shouldInstaKill = false, isRotational = false;


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
    public PIDController(SpeedController m, PIDSource s, double kP, double kI, double kD) {
        this.m = (m != null) ? m : new FakeMotor();
        this.s = s;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.min = 0;
        this.max = 1;
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
    public PIDController(SpeedController m, PIDSource s, double kP, double kI, double kD, double min, double max) {
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

        p = t - s.getForPID();

        if (inverted) p *= -1;

        startingSign = Utils.sign(p);
    }

    /**
     * Disables controllers of the motor. This also stops the motor
     */
    public void disable() {
        enabled = false;
        m.set(0);
    }

    /**
     * Checks if this PIDController is enabled.
     *
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
        i = 0;
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
     * Set the PIDController to invert its input
     *
     * @param isRotational Whether the PIDController should treat itself as rotational
     * @return This PIDController (for method chaining)
     */
    public PIDController setRotational(Boolean isRotational) {
        this.isRotational = isRotational;
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
        return m.get();
    }

    /**
     * Gets the current value of the PID source.
     *
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
     * @param kP Proportional tuning variable. Set to 0 to disable the P term.
     * @param kI Integral tuning variable. Set to 0 to disable to I term.
     * @param kD Derivative tuning variable. Set to 0 to disable the D term.
     */
    public void reTune(double kP, double kI, double kD) {
        reset();
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    /**
     * Set the tolerance for determining if the loop is finished. Default is 1000
     *
     * @param tolerance The tolerance, in whatever units the PIDSource provides
     */
    public void setFinishedTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public void setShouldInstaKill(Boolean b) {
        shouldInstaKill = b;
    }

    /**
     * Checks if this PIDController is done.
     *
     * @return If this PIDController is done.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Defines code that runs when this PID loop is done.
     *
     * @param r The code to be run.
     * @return This PIDController.
     */
    public PIDController whenFinished(Runnable r) {
        whenFinished = r;
        return this;
    }

    /**
     * Sets the code that is run every loop of the PID.
     *
     * @param r The code to run.
     */
    public void setExtraCode(Runnable r) {
        extraCode = r;
    }

    public void log() {
        if (enabled)
            Log.t("Target: " + getTarget() + "; Current Value: " + getCurrentSourceValue() + "; Motor Setpoint: " + getMotorPower());
    }

    long timeOfZero = 0;

    public void logVerbose() {
        if (enabled)
            synchronized (verboseLogLock) {
                Log.t("P: " + Utils.roundToDecimals(p*kP, 3)
                        + ", I: " + Utils.roundToDecimals(i*kI, 3)
                        + ", D: " + Utils.roundToDecimals(d*kD, 3)
                        + ", out " + Utils.roundToDecimals(power, 3)
                        + ", set: " + Utils.roundToDecimals(t, 3)
                        + ", sensor: " + Utils.roundToDecimals(s.getForPID(), 3));
                //Log.t(String.format("P %d; I %d; D %d", p, i, d));
            }
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
            if (enabled) {
                synchronized (verboseLogLock) {
                    p = t - s.getForPID();

                    if (inverted) p *= -1;
                    if (isRotational && p >= 180) p -= 360;
                    if (isRotational && p <= -180) p += 360;

                    if(isRotational && Math.abs(p) < 36) i += p;
                    else if(!isRotational) i += p;
                    d = lastP - p;
                    lastP = p;

                    i = Math.abs(i * kI) > 1 ? (max / kI) * Utils.sign(i) : i; //Prevent i windup

                    if (Utils.sign(power) != Utils.sign(p)) {
                        //power = 0;
                        //Log.i("Sign isn't equal to P. NOT compensating by setting motor power to 0.");
                    }

                    if ((Utils.sign(i) != Utils.sign(p)) && isRotational) {
                        i = 0;
                        //Log.i("Sign of I isn't equal to P. Compensating by setting I to 0.");
                    }


                    double setpoint = p * kP + i * kI - d * kD;
                    setpoint = Utils.minMax(setpoint, 0, 1);

                    power = Utils.minMax(setpoint, min, max);

                    //If our target is 0 and our target is a rate, set the motor to 0, since that'll get us a rate of 0 easily
                    if (isRate && getTarget() == 0) {
                        power = 0;
                        p = 0;
                        i = 0;
                        d = 0;
                        lastP = 0;
                    }

                    m.set(power);

                    try {
                        if (extraCode != null) {
                            Utils.runInOwnThread(extraCode);
                        }
                    } catch (Exception e) {
                        Log.e("PIDController.extraCode Runnable error!");
                        e.printStackTrace();
                    }

                    boolean doneAtm = Math.abs(p) <= tolerance;

                    if (doneAtm) {
                        if ( timeOfZero == 0)
                        timeOfZero = System.currentTimeMillis();
                    } else {
                        timeOfZero = 0;
                    }

                    if (timeOfZero != 0 && System.currentTimeMillis() - timeOfZero > 250) { //500
                        done = true;
                    } else {
                        done = false;
                    }

                    if ((Utils.sign(p) != startingSign) && shouldInstaKill) disable();

                    if (done && whenFinished != null) {
                        try {
                            whenFinished.run();
                        } catch (Exception e) {
                            Log.e("PIDController.whenFinished Runnable error!");
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                done = true;
                if (enabled)
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
