package com.explodingbacon.bcnlib.event;

import com.explodingbacon.bcnlib.framework.ExtendableRobot;

public class TeleopStartEvent extends Event {

    private ExtendableRobot r;

    public TeleopStartEvent(ExtendableRobot r) { this.r = r; }

    public ExtendableRobot getRobot() { return r; }
}