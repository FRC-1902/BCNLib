package com.explodingbacon.bcnlib.event;

import com.explodingbacon.bcnlib.framework.ExtendableRobot;

public class AutonomousStartEvent extends Event {

    private ExtendableRobot r;

    public AutonomousStartEvent(ExtendableRobot r) { this.r = r; }

    public ExtendableRobot getRobot() { return r; }

}
