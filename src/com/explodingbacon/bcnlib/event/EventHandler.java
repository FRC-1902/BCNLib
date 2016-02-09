package com.explodingbacon.bcnlib.event;

import com.explodingbacon.bcnlib.utils.CodeThread;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that handles the Event system. This has the functions for firing Events and is in charge of calling the
 * appropriate EventListener method for said Events.
 *
 * @author Ryan Shavell
 * @version 2016.2.1
 */

public abstract class EventHandler {

    private static List<EventWithArgs> events = new ArrayList<>();
    private static final Object EVENTS_RW = new Object();
    private static CodeThread thread;
    private static Object listener = null;
    private static boolean init = false;

    /**
     * Initializes the EventHandler.
     * @param l The class that contains the EventListener methods.
     */
    public static void init(Object l) { //TODO: Check how memory intensive this is
        listener = l;
        thread = new CodeThread() {
            @Override
            public void code() {
                synchronized (EVENTS_RW) {
                    if (!events.isEmpty()) {
                        Method[] methods = listener.getClass().getMethods();
                        for (Method m : methods) {
                            if (m.isAnnotationPresent(EventListener.class)) {
                                for (EventWithArgs ewa : events) {
                                    List<Class> params = new ArrayList<>();
                                    Collections.addAll(params, m.getParameterTypes());
                                    if (params.contains(ewa.e.getClass())) {
                                        CodeThread c = new CodeThread(false) { //TODO: Check if this works for calling the method in it's own temporary thread
                                            @Override
                                            public void code() {
                                                try {
                                                    m.invoke(listener, ewa.args);
                                                } catch (Exception e) {}
                                            }
                                        };
                                        c.start();
                                        break;
                                    }
                                }
                            }
                        }
                        events.clear();
                    }
                }
            }
        };
        thread.start();
        init = true;
    }

    /**
     * Adds an Event to the event firing queue.
     * @param e The Event to be added.
     * @param args Optional arguments that are currently not used.
     */
    public static void fireEvent(Event e, Object... args) {
        synchronized (EVENTS_RW) {
            events.add(new EventWithArgs(e, args));
        }
    }

    /**
     * Checks if the EventHandler is initialized.
     * @return If the EventHandler is initialized.
     */
    public static boolean isInitialized() {
        return init;
    }

    static class EventWithArgs {
        protected Event e;
        protected Object[] args; //TODO: Either make this useful or delete it

        EventWithArgs(Event e, Object... args) {
            this.e = e;
            this.args = args;
        }
    }
}