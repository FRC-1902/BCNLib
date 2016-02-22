package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.actuators.Motor;
import com.explodingbacon.bcnlib.javascript.Javascript;

import java.util.List;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 */
public abstract class Subsystem {
    /**
     * <code>Command</code> object that is holding controllers of this <code>Subsystem</code>. <code>Null</code> when this
     * <code>Subsystem</code> is able to be changed
     */
    public Subsystem() {
        RobotCore.subsystems.add(this);
        if (Javascript.isInit()) {
            Javascript.importClass(getClass());
        }
    }

    /**
     * Gets the name of this Subsystem.
     * @return The name of this Subsystem.
     */
    public String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Void that should onStop all motion in this <code>Subsystem</code> when called.
     * <p>
     * Called automatically when a <code>Command</code> takes controllers of this <code>Subsystem</code>, and can be called
     * by any <code>Command</code> using any <code>Subsystem</code> for ease of controllers.
     */
    public abstract void stop();

    public abstract List<Motor> getAllMotors();
}
