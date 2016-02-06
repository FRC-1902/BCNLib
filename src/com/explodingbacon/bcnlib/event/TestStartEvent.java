package com.explodingbacon.bcnlib.event;

import com.explodingbacon.bcnlib.framework.ExtendableRobot;

public class TestStartEvent extends Event {

    private ExtendableRobot r;

    public TestStartEvent(ExtendableRobot r) { this.r = r; }

    public ExtendableRobot getRobot() { return r; }

}
