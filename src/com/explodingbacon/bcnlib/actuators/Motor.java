package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.AbstractOI;
import com.explodingbacon.bcnlib.framework.Command;
import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.sensors.MotorEncoder;
import com.explodingbacon.bcnlib.utils.NetTuner;
import com.explodingbacon.bcnlib.utils.Utils;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A class for controlling Motors on the Robot.
 *
 * @author Ryan Shavell
 * @version 2016.2.29
 */

public class Motor {

    protected SpeedController sc = null;
    protected int channel = -1;
    protected boolean reverse = false;
    protected boolean isTuning = false;
    protected boolean isFiltered = false;
    protected Runnable onStopIfNoUser = null;
    protected double smoothing;
    protected double filteredSetpoint;
    protected double filterTarget;
    protected String tuningKey = "";
    protected String name = "";
    protected MotorEncoder encoder = null;
    protected Thread t;
    protected Command user = null;
    private static List<Motor> allMotors = new ArrayList<>();

    /**
     * Standard constructor for Motor.
     * @param clazz The class of this Motor.
     * @param channel The channel (PWM or CAN) of this Motor.
     * @param <T> A class that extends SpeedController.
     */
    public <T extends SpeedController> Motor(Class<T> clazz, int channel) {
        this.channel = channel;
        try {
            Constructor[] constructors = clazz.getConstructors();
            if (constructors.length > 0) {
                for (Constructor c : constructors) {
                    Class[] paramTypes = c.getParameterTypes();
                    if (paramTypes.length == 1 && (paramTypes[0] == Integer.class || paramTypes[0] == int.class)) {
                        sc = (SpeedController) c.newInstance(channel);
                        break;
                    }
                }
                if (sc == null) {
                    Log.e("The class given to Motor, \"" + clazz.getSimpleName() + "\", does not have a constructor that accepts an Integer as its only argument!");
                }
            }
        } catch (Exception e) {
            Log.e("Motor constructor error!");
            e.printStackTrace();
        }
        init();
    }

    /**
     * Constructor a Motor using an existing SpeedController object.
     * @param s The SpeedController object.
     */
    public Motor(SpeedController s) {
        sc = s;
        if (s.getInverted()) {
            s.setInverted(false);
            setReversed(true);
        }
        init();
    }

    /**
     * The constructor to be used when an implementation of Motor does not require the use of a SpeedController. If this
     * constructor is being used, you'll need to override getPower() and setPower() to not use the "sc" variable.
     */
    protected Motor() {
        init();
    }

    /**
     * Initializes this Motor.
     */
    private void init() {
        allMotors.add(this);
    }

    /**
     * Sets the name of this Motor.
     * @param n The new name of the Motor.
     * @return This Motor (for method chaining and variable saving)
     */
    public Motor setName(String n) {
        name = n;
        return this;
    }

    /**
     * Gets the current power of this Motor. Motor power ranges from 1 to -1.
     * @return The current power of this Motor.
     */
    public double getPower() {
        return sc.get();
    }

    /**
     * Sets the current power of this Motor. Motor power ranges from 1 to -1.
     * @param d The new power of this Motor.
     */
    public void setPower(double d) {
        if (isTuning) return;
        if(isFiltered) {
            filterTarget = d;
            return;
        }

        sc.set(reverse ? -d : d);
        //SmartDashboard.putNumber(getName(), getPower());
    }

    /**
     * Sets the current power of this Motor. Motor power ranges from 1 to -1. This method should only be called from
     * BCNLib packages, since it overrides any restrictions.
     *
     * @param t A valid NetTuner, for access controllers.
     * @param d The new power of this Motor
     */
    public void setPower(NetTuner t, double d) {
        if (t == null) return;
        sc.set(reverse ? -d : d);
    }

    /**
     * Checks if this Motor is reversed.
     * @return If this Motor is reversed.
     */
    public boolean isReversed() {
        return reverse;
    }

    /**
     * Sets this Motor's reversal status.
     * @param b Whether or not this Motor should be reversed.
     */
    public void setReversed(boolean b) {
        reverse = b;
    }

    /**
     * Checks if this Motor is in tuning mode.
     * @return If this Motor is in tuning mode.
     */
    public boolean isTuning() {
        return isTuning;
    }

    /**
     * Puts this Motor into tuning mode.
     */
    public void tune() {
        tune(getName());
    }

    /**
     * Puts this Motor into tuning mode under a specific key.
     * @param k The specific key.
     */
    public void tune(String k) {
        AbstractOI.tuner.tune(k, this);
        isTuning = true;
        tuningKey = k;
    }

    /**
     * Takes this Motor out of tuning mode.
     */
    public void stopTuning() {
        AbstractOI.tuner.stopTune(tuningKey);
        isTuning = false;
        tuningKey = "";
    }

    /**
     * Stops this Motor.
     */
    public void stopMotor() {
        sc.stopMotor();
    }

    /**
     * Gets the MotorEncoder for this Motor. CANTalons only.
     *
     * @return The MotorEncoder for this Motor.
     */
    public MotorEncoder getEncoder() {
        if (encoder == null) {
            if (sc instanceof CANTalon) {
                encoder = new MotorEncoder((CANTalon) sc);
            } else {
                Log.e("Called Motor.getEncoder() on a Motor that is not a CANTalon! (Motor is a " + getClass().getSimpleName() + ")");
                return null;
            }
        }
        return encoder;
    }

    /**
     * Gets the output current of this Motor. CANTalons only.
     *
     * @return The output current of this Motor.
     */
    public double getOutputCurrent() {
        if (sc instanceof CANTalon) {
            return ((CANTalon) sc).getOutputCurrent();
        } else {
            Log.e("Called Motor.getOutputCurrent() on a Motor that is not a CANTalon! (Motor is a " + getClass().getSimpleName() + ")");
            return 0;
        }
    }

    public double getOutputVoltage() {
        if (sc instanceof CANTalon) {
            return ((CANTalon) sc).getOutputVoltage();
        } else {
            Log.e("Called Motor.getOutputVoltage() on a Motor that is not a CANTalon! (Motor is a " + getClass().getSimpleName() + ")");
            return 0;
        }
    }

    /**
     * Gets the watts of this Motor. CANTalons only.
     *
     * @return The watts of this Motor.
     */
    public double getWatts() {
        if (sc instanceof CANTalon) {
            return getOutputCurrent() * getOutputVoltage();
        } else {
            Log.e("Called Motor.getWatts() on a Motor that is not a CANTalon! (Motor is a " + getClass().getSimpleName() + ")");
            return 0;
        }
    }

    /**
     * Ramps up this Motor, from 0 to 1, over a certain amount of seconds.
     *
     * @param seconds How may seconds the ramping up will take.
     */
    public void rampUpWait(int seconds) {
        rampUpWait(seconds, false, null);
    }

    /**
     * Ramps up this Motor over a certain amount of seconds.
     *
     * @param seconds How may seconds the ramping up will take.
     * @param reversed If the Motor should run backwards instead of forwards.
     */
    public void rampUpWait(int seconds, boolean reversed) {
        rampUpWait(seconds, reversed, null);
    }

    /**
     * Ramps up this Motor over a certain amount of seconds.
     *
     * @param seconds How may seconds the ramping up will take.
     * @param reversed If the Motor should run backwards instead of forwards.
     * @param function Code that analyzes the current state of the ramp up, and can possibly cancel the ramping.
     */
    public void rampUpWait(int seconds, boolean reversed, Function<RampUpData, RampUpData> function) {
        //double currentSpeed = 0;
        double startMillis = System.currentTimeMillis();
        double goalMillis = startMillis + seconds * 1000;
        setPower(0);
        while (System.currentTimeMillis() <= goalMillis) {
            try {
                Thread.sleep(25);
                //currentSpeed += (currentMillis / goalMillis) * (reversed ? -1 : 1);
                double speed = (System.currentTimeMillis() - startMillis) / goalMillis;
                setPower(speed);
                RampUpData md = function.apply(new RampUpData(this));
                if (md.isCancelled()) break;
            } catch (Exception e) {
                Log.e("Motor.rampUpWait() error!");
                e.printStackTrace();
            }
        }
        setPower(0);
    }

    public class RampUpData {
        private Motor motor;
        private boolean cancel = false;

        private RampUpData(Motor m) {
            motor = m;
        }

        public Motor getMotor() { return motor; }
        public boolean isCancelled() { return cancel; }
        public void setCancelled(boolean b) { cancel = b; }
    }

    protected Runnable lowPassFilter = () -> {
        while(isFiltered) {
            filteredSetpoint += (filterTarget - sc.get()) / smoothing;
            filteredSetpoint = Utils.minMax(filteredSetpoint, 0.01, 1);
            sc.set(filteredSetpoint);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    };

    /**
     * Enables low-pass filtering on this Motor.
     *
     * @param smoothing The higher this number is, the longer it takes this Motor to reach it's intended speed.
     */
    public void setFiltered(double smoothing) {
        this.smoothing = smoothing < 1 ? 1 : smoothing;
        this.isFiltered = true;

        t = new Thread(lowPassFilter);
    }

    /**
     * Stops low-pass filtering on this Motor.
     */
    public void stopFiltering() {
        this.isFiltered = false;
    }

    /**
     * Checks if low-pass filtering is enabled on this Motor.
     *
     * @return If low-pass filtering is enabled on this Motor.
     */
    public Boolean isFiltered() {
        return isFiltered;
    }

    /**
     * Sets the current Command that is using this Motor.
     * @param c The Command that is using this Motor, or null if no command is using this Motor.
     */
    public void setUser(Command c) {
        user = c;
        if (user == null && onStopIfNoUser != null) {
            try {
                onStopIfNoUser.run();
            } catch (Exception e) {
                Log.e("Motor.onNoUser Runnable error!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the current user to null.
     */
    public void clearUser() {
        setUser(null);
    }

    /**
     * Makes it so this Motor will stop moving if it has no user.
     */
    public void setStopOnNoUser() {
       onNoUser(() -> setPower(0));
    }

    /**
     * Sets the code that will run if this Motor has no user.
     */
    public void onNoUser(Runnable r) {
        onStopIfNoUser = r;
    }

    /**
     * Checks if this Motor is currently being used by a Command.
     *
     * @return If this Motor is currently being used by a Command.
     */
    public boolean isBeingUsed()  {
        return user != null;
    }

    /**
     * Checks if this Motor is usable by a Command. This Motor is usable by the Command if the Command is already using
     * this Motor or if there is no user.
     *
     * @param c The Command
     * @return If this Motor is usable by the Command.
     */
    public Boolean isUseableBy(Command c) {
        return (user == c || !isBeingUsed());
    }

    public boolean claim(Command c) {
        if (isUseableBy(c)) {
            setUser(c);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets this Motor's class.
     * @return This Motor's class.
     */
    public Class getMotorClass() {
        if (sc != null) {
            return sc.getClass();
        } else {
            return getClass();
        }
    }

    /**
     * Gets the channel this Motor is on.
     * @return The channel this Motor is on.
     */
    public int getChannel() {
        return channel;
    }

    /**
     * Gets the WPILib SpeedController object that this class is wrappering.
     * @return The WPILib SpeedController object that this class is wrappering.
     */
    public SpeedController getWPISpeedController() {
        return sc;
    }

    /**
     * Gets the name of this Motor.
     * @return The name of this Motor.
     */
    public String getName() {
        if (!name.equals("")) {
            return name;
        } else {
            return "" + getMotorClass().getSimpleName() + "_" + channel;
        }
    }

    /**
     * Gets all the Motors that have been created.
     * @return All the Motors that have been created.
     */
    public static List<Motor> getAllMotors() {
        return new ArrayList<>(allMotors);
    }
}
