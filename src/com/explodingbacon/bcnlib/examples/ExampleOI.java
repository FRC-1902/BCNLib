package com.explodingbacon.bcnlib.examples;

import com.explodingbacon.bcnlib.framework.Button;
import com.explodingbacon.bcnlib.framework.OI;
import com.explodingbacon.bcnlib.utils.Xbox;

public class ExampleOI extends OI {

    Xbox xbox = new Xbox(0);
    Button myButton;

    public ExampleOI() {
        myButton = new Button(xbox, 4);

        whenPressed(myButton, new ExampleCommand());
        whenReleased(myButton, new ExampleCommand());
        whileHeld(myButton, new ExampleCommand());

        start();
    }
}
