package com.explodingbacon.bcnlib.event;

import com.explodingbacon.bcnlib.controllers.Button;

public class ButtonPressEvent extends Event {

    private Button button;

    public ButtonPressEvent(Button b) {
        button = b;
    }

    public Button getButton() {
        return button;
    }
}
