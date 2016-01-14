package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.framework.Timer;

/**
 * A class that allows us to easily write code that will run in its own separate thread.
 *
 * @author Ryan Shavell
 * @version 2016.1.10
 */

public class CodeThread implements Runnable {

    Thread t;
    boolean stop = false;
    boolean running = false;
    double seconds = -1;
    Timer timer = null;

    public CodeThread() {}

    /**
     * When you give CodeThread seconds, it will run until that many seconds have gone by and then terminate.
     * @param seconds How many seconds this CodeThread will last.
     */
    public CodeThread(double seconds) {
        this.seconds = seconds;
    }

    /**
     * Start this <code>CodeThread</code>
     */
    public void start() {
        t = new Thread(this);
        t.start();
        final CodeThread self = this;
        if (seconds > -1) {
            timer = new Timer(seconds, new TimerUser() {
                @Override
                public void timer() {
                    self.stop();
                }

                @Override
                public void timerStop() {
                    timer = null;
                }
            });
        }
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