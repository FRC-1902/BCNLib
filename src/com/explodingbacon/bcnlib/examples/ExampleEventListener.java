package com.explodingbacon.bcnlib.examples;

import com.explodingbacon.bcnlib.event.AutonomousStartEvent;
import com.explodingbacon.bcnlib.event.ButtonPressEvent;
import com.explodingbacon.bcnlib.event.EventListener;
import com.explodingbacon.bcnlib.event.TeleopStartEvent;
import com.explodingbacon.bcnlib.framework.Button;

public class ExampleEventListener {

    @EventListener
    public void teleopStart(TeleopStartEvent e) {
        //TODO: Implement
    }

    @EventListener
    public void autonomousStart(AutonomousStartEvent e) {
        //TODO: Implement
    }

    @EventListener
    public void buttonPressed(ButtonPressEvent e) {
        Button b = e.getButton();
        //TODO: Implement
    }
}
