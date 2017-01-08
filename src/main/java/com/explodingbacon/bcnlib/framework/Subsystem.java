package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.actuators.Motor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a Robot subsystem.
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public abstract class Subsystem {

    private boolean init = false;
    /**
     * <code>Command</code> object that is holding controllers of this <code>Subsystem</code>. <code>Null</code> when this
     * <code>Subsystem</code> is able to be changed
     */
    public Subsystem() {
        RobotCore.subsystems.add(this);
        init = true;
    }

    /**
     * Checks if this Subsystem is initialized.
     * @return If this Subsystem is initialized.
     */
    public boolean isInit() {
        return init;
    }

    /**
     * Gets the name of this Subsystem.
     * @return The name of this Subsystem.
     */
    public String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Runs when the Robot becomes enabled.
     */
    public abstract void enabledInit();

    /**
     * Runs when the Robot becomes disabled.
     */
    public abstract void disabledInit();

    /**
     * Void that should onStop all motion in this <code>Subsystem</code> when called.
     * <p>
     * Called automatically when a <code>Command</code> takes controllers of this <code>Subsystem</code>, and can be called
     * by any <code>Command</code> using any <code>Subsystem</code> for ease of controllers.
     */
    public abstract void stop();

    /**
     * Gets all the Motors that are stored in this Subsystem. TODO: Automate this via pulling all the Fields in this class and checking which ones are Motors/subsets of Motor
     * @return All the Motors that are stored in this Subsystem.
     */
    public abstract List<Motor> getAllMotors();

    /**
     * An experimental version of getAllMotors(). Instead of subsets of Subsystem having to manually implement it, this version of the function iterates through the Substem's
     * variables and returns all of the Motor varaible's functions. TODO: Test this.
     *
     * @return All the Motors that are stored in this Subsystem's variables.
     */
    public List<Motor> experimentalGetAllMotors() {
        List<Motor> motors = new ArrayList<>();
        Field[] fields = getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getType().isAssignableFrom(Motor.class)) {
                try {
                    motors.add((Motor) f.get(this));
                } catch (Exception e) {
                    Log.e("Subsystem.experimentalGetAllMotors() exception!");
                    e.printStackTrace();
                }
            }
        }
        return motors;
    }
}
