package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Keeps a subsystem from being able to actuate any of it's actuators by constantly calling the subsystem's stop() function.
 * TODO: Currently not used and may be subject to deletion.
 *
 * @author Ryan Shavell
 * @version 2016.2.16
 */

public class StopCommand extends Command {

    /**
     * Creates a StopCommand.
     * @param s
     */
    public StopCommand(Subsystem s) {
        requires(s);
    }

    @Override
    public void onInit() {}

    @Override
    public void onLoop() {
        if (!SmartDashboard.getBoolean(requiredSub.getName(), false)) { //If the subsystem is not enabled in SmartDashboard
            requiredSub.stop();
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public boolean isFinished() {
        return !RobotCore.isEnabled() || RobotCore.getMode() != Mode.TEST;
    }
}
