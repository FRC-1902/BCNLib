package com.explodingbacon.bcnlib.event;

public abstract class Event {

    /**
     * Fires this Event. This calls "EventHandler.fireEvent(this)"
     */
    public void fire() {
        EventHandler.fireEvent(this);
    }
}
