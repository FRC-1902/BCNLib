package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.explodingbacon.bcnlib.framework.Robot.*;

/**
 * @author Ryan Shavell
 * @version 2016.1.13
 */

public class StopCommand extends Command {

    public StopCommand(Subsystem s) {
        requires(s);
    }

    @Override
    public void init() {}

    @Override
    public void loop() {
        if (!SmartDashboard.getBoolean(requiredSub.getName(), false)) { //If the subsystem is not enabled in SmartDashboard
            requiredSub.stop();
        }
    }

    @Override
    public void stop() { requiredSub.releaseControl(this); }

    @Override
    public boolean isFinished() {
        return !Robot.getEnabled() || Robot.getMode() != Mode.TEST;
    }
}
