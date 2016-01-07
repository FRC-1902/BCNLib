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
        while (true) {
            if (stop) break;
            code();
        }
        t = null;
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

}