package com.explodingbacon.bcnlib.utils;

/**
 * A class that allows us to easily write code that will run in its own separate thread.
 *
 * @author Ryan Shavell
 * @version 2016.2.13
 */

public class CodeThread implements Runnable {

    private Thread t;
    private boolean loop = true;
    private boolean stop = false;
    private boolean running = false;

    private Runnable passedCode = null;

    /**
     * Creates a CodeThread.
     */
    public CodeThread() {}

    /**
     * Creates a CodeThread that will repeatedly run r.
     * @param r The code to be repeatedly.
     */
    public CodeThread(Runnable r) {
        passedCode = r;
    }

    /**
     * Creates a CodeThread.
     * @param l If the CodeThread will loop.
     */
    public CodeThread(boolean l) {
        loop = l;
    }


    /**
     * Creates a CodeThread that will run r (possibly repeatedly).
     * @param r The code to be run.
     */
    public CodeThread(boolean l, Runnable r) {
        passedCode = r;
        loop = l;
    }


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
            if (passedCode != null) {
                passedCode.run();
            } else {
                code();
            }
            if (!loop) break;
        }
        stop = false;
        t = null;
        running = false;
    }

    /**
     * Override this with what code you want to run in the thread.
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