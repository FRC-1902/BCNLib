package com.explodingbacon.bcnlib.event;

import com.explodingbacon.bcnlib.controllers.Button;

public class ButtonReleaseEvent extends Event {

    private Button button;

    public ButtonReleaseEvent(Button b) { button = b; }

    public Button getButton() {
        return button;
    }
}