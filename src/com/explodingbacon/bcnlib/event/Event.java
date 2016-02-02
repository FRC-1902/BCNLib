package com.explodingbacon.bcnlib.event;

public class Event {

    /**
     * Fires this Event. This just calls "EventHandler.fireEvent(this)"
     */
    public void fire() {
        EventHandler.fireEvent(this);
    }
}
