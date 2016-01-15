package com.explodingbacon.bcnlib.examples;

import com.explodingbacon.bcnlib.framework.*;

public class ExampleOI extends OI {

    NetGamepad xbox = new NetGamepad("xbox", 2, 10);

    Button shootButton = xbox.getButton(0);
    Button climbButton = xbox.getButton(1);
    Button intakeButton = xbox.getButton(2);

    NetJoystick joy = xbox.getJoystick(0);

    public ExampleOI() {
        whenPressed(shootButton, new ExampleCommand());
        whenReleased(climbButton, new ExampleCommand());
        whileHeld(intakeButton, new ExampleCommand());

        start();
    }
}
