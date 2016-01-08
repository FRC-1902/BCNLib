package com.explodingbacon.bcnlib.utils;

/**
 * A class that allows us to easily write code that will run in its own separate thread.
 *
 * @author Ryan Shavell
 * @version 2016.1.6
 */

public class CodeThread implements Runnable {

    Thread t;
    boolean stop = false;
    boolean running = false;

    /**
     * Start this <code>CodeThread</code>
     */
    public void start() {
        t = new Thread(this);
        t.start();
    }

    /**
     * Don't touch this, we take care of it.
     */
    @Override
    public void run() {
        running = true;
        while (true) {
            if (stop) break;
            code();
        }
        stop = false;
        t = null;
        running = false;
    }

    /**
     * Override this with what code you want to run looped.
     */
    public void code() {}

    /**
     * Stop execution of this <code>CodeThread</code>
     */
    public void stop() {
        stop = true;
    }

    /**
     * Returns whether or not this CodeThread is currently running.
     * @return Whether or not this CodeThread is currently running.
     */
    public boolean isRunning() {
        return running;
    }

}