package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.javascript.Javascript;

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
        RobotCore.subsystems.add(this); //var RobotCore = Java.type('com.explodingbacon.bcnlib.framework.RobotCore');
        if (Javascript.isInit()) {
            String js = String.format("var %s = Java.type('%s');", getClass().getSimpleName(), getClass().getPackage());
            Log.d("Subsystem package: " + getClass().getPackage());
            Javascript.run(js);
        }
    }

    private Command inUseBy;

    /**
     * Allows a <code>Command</code> to take exclusive controllers of a <code>Subsystem</code>.
     *
     * @param c Subclass of <code>Command</code> that is taking exclusive controllers of this <code>Subsystem</code>
     */
    public void takeControl(Command c) {
        if (this.inUseBy == null)
            this.inUseBy = c;
        else
            throw new RuntimeException("Command attempted to take control of a subsystem that was already being controlled");
    }

    /**
     * Allows a <code>Command</code> to release controllers of a <code>Subsystem</code>.
     *
     * @param c Subclass of <code>Command</code> that is releasing controllers of this <code>Subsystem</code>
     */
    public void releaseControl(Command c) {
        if (c == inUseBy) {
            inUseBy = null;
        } else {
            throw new RuntimeException("Command attempted to release a Subsystem it was not in control of");
        }
    }

    /**
     * Makes whatever command that is controlling this subsystem onStop and release it.
     */
    public void releaseControl() {
        if (inUseBy != null) {
            inUseBy.onStop();
        }
    }

    /**
     * Gets whether this <code>Subsystem</code> is in use.
     *
     * @return True if this <code>Subsystem</code> is in use
     */
    public boolean isInUse() {
        return inUseBy != null;
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
}
