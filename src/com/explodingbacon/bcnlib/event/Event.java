package com.explodingbacon.bcnlib.event;

/**
 * An Event class. Events are fired off by the EventHandler and caught by EventListener functions.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public abstract class Event {

    /**
     * Fires this Event. This calls "EventHandler.fireEvent(this)"
     */
    public void fire() {
        EventHandler.fireEvent(this);
    }
}
