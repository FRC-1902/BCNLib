package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.explodingbacon.bcnlib.framework.ExtendableRobot.*;

/**
 * @author Ryan Shavell
 * @version 2016.1.13
 */

public class StopCommand extends Command {

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
    public void onStop() { requiredSub.releaseControl(this); }

    @Override
    public boolean isFinished() {
        return !ExtendableRobot.getEnabled() || ExtendableRobot.getMode() != Mode.TEST;
    }
}
