package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.framework.Command;
import com.explodingbacon.bcnlib.framework.Robot;
import com.explodingbacon.bcnlib.framework.Subsystem;

public class StopCommand extends Command {

    public StopCommand(Subsystem s) {
        requires(s);
    }

    @Override
    public void init() {
    }

    @Override
    public void loop() {
        requiredSub.stop();
    }

    @Override
    public void stop() {
        requiredSub.releaseControl(this);
    }

    @Override
    public boolean isFinished() {
        return !Robot.getEnabled();
    }

}
