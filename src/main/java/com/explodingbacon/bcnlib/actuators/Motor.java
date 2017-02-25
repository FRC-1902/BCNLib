package com.explodingbacon.bcnlib.actuators;

import com.ctre.CANTalon;
import com.explodingbacon.bcnlib.framework.Command;
import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.sensors.MotorEncoder;
import edu.wpi.first.wpilibj.SpeedController;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A class for controlling Motors on the Robot.
 *
 * @author Ryan Shavell
 * @version 2017.1.8
 */

public class Motor extends Usable {

    protected SpeedController sc = null;
    protected int channel = -1;
    protected boolean reverse = false;
    protected boolean isTuning = false;
    protected boolean isFiltered = false;
    protected double filterTarget;
    protected String name = "";
    protected MotorEncoder encoder = null;
    protected Thread t;
    protected Command user = null;
    private static List<Motor> allMotors = new ArrayList<>();

    /**
     * Standard constructor for Motor.
     *
     * @param clazz   The class of this Motor.
     * @param channel The channel (PWM or CAN) of this Motor.
     * @param <T>     A class that extends SpeedController.
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
     *
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
     *
     * @param n The new name of the Motor.
     * @return This Motor (for method chaining and variable saving)
     */
    public Motor setName(String n) {
        name = n;
        return this;
    }

    /**
     * Gets the current power of this Motor. Motor power ranges from 1 to -1.
     *
     * @return The current power of this Motor.
     */
    public double getPower() {
        return sc.get();
    }

    /**
     * Sets the current power of this Motor. Motor power ranges from 1 to -1.
     *
     * @param d The new power of this Motor.
     */
    public void setPower(double d) {
        if (isTuning) return;
        if (isFiltered) {
            filterTarget = d;
            return;
        }

        sc.set(reverse ? -d : d);
        //SmartDashboard.putNumber(getName(), getPower());
    }

    /**
     * Checks if this Motor is reversed.
     *
     * @return If this Motor is reversed.
     */
    public boolean isReversed() {
        return reverse;
    }

    /**
     * Sets this Motor's reversal status.
     *
     * @param b Whether or not this Motor should be reversed.
     */
    public void setReversed(boolean b) {
        reverse = b;
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
                Log.e("Called Motor.getEncoder() on a Motor that is not a CANTalon! (Motor is a " + getMotorClass().getSimpleName() + ")");
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
            Log.e("Called Motor.getOutputCurrent() on a Motor that is not a CANTalon! (Motor is a " + getMotorClass().getSimpleName() + ")");
            return 0;
        }
    }

    public double getOutputVoltage() {
        if (sc instanceof CANTalon) {
            return ((CANTalon) sc).getOutputVoltage();
        } else {
            Log.e("Called Motor.getOutputVoltage() on a Motor that is not a CANTalon! (Motor is a " + getMotorClass().getSimpleName() + ")");
            return 0;
        }
    }

    /**
     * Sets if this Motor should be on brake mode. CANTalons only.
     *
     * @param b If this Motor should be on brake mode.
     */
    public void setBrakeMode(boolean b) {
        if (sc instanceof CANTalon) {
            ((CANTalon) sc).enableBrakeMode(b);
        } else {
            Log.e("Called Motor.setBrakeMode() on a Motor that is not a CANTalon! (Motor is a " + getMotorClass().getSimpleName() + ")");
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
            Log.e("Called Motor.getWatts() on a Motor that is not a CANTalon! (Motor is a " + getMotorClass().getSimpleName() + ")");
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
     * @param seconds  How may seconds the ramping up will take.
     * @param reversed If the Motor should run backwards instead of forwards.
     */
    public void rampUpWait(int seconds, boolean reversed) {
        rampUpWait(seconds, reversed, null);
    }

    /**
     * Ramps up this Motor over a certain amount of seconds.
     *
     * @param seconds  How may seconds the ramping up will take.
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

        public Motor getMotor() {
            return motor;
        }

        public boolean isCancelled() {
            return cancel;
        }

        public void setCancelled(boolean b) {
            cancel = b;
        }
    }

    /**
     * Makes it so this Motor will stop moving if it has no user.
     */
    public void setStopOnNoUser() {
        onNoUser(() -> setPower(0));
    }

    /**
     * Gets this Motor's class.
     *
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
     *
     * @return The channel this Motor is on.
     */
    public int getChannel() {
        return channel;
    }

    /**
     * Gets the WPILib SpeedController object that this class is wrappering, if any.
     *
     * @return The WPILib SpeedController object that this class is wrappering.
     */
    public SpeedController getWPISpeedController() {
        return sc;
    }

    /**
     * Gets the name of this Motor.
     *
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
     *
     * @return All the Motors that have been created.
     */
    public static List<Motor> getAllMotors() {
        return new ArrayList<>(allMotors);
    }
}
