package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.framework.Log;

/**
 * A class that lets you set a timer that will be called once (or every) X seconds.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
 */

public class Timer implements Runnable {

    private double seconds;
    private Runnable r;
    private boolean loop = true;
    private boolean stop = false;
    private Thread thread;

    /**
     * Creates a Timer that calls method "c" after X amount of seconds have gone by.
     *
     * @param seconds How many seconds the timer will wait before calling "c".
     * @param r A Runnable that represents the function to be called.
     */
    public Timer(double seconds, Runnable r) {
        this.seconds = seconds;
        this.r = r;
    }

    /**
     * Creates a Timer that calls method "c" after X amount of seconds have gone by (possibly multiple times if looping)
     *
     * @param seconds How many seconds the timer will wait before calling "c".
     * @param loop If the timer should keep looping.
     * @param r A Runnable that represents the function to be called.
     */
    public Timer(double seconds, boolean loop, Runnable r) {
        this.seconds = seconds;
        this.loop = loop;
        this.r = r;
    }

    /**
     * Starts the Timer.
     *
     * @return This timer. To be used for method chaining.
     */
    public Timer start() {
        thread = new Thread(this);
        thread.start();
        return this;
    }

    /**
     * The onLoop that makes this timer work. Don't call/override this.
     */
    @Override
    public void run() {
        while (true) {
            try {
                if (stop) break;
                Thread.sleep((long) seconds * 1000);
                try {
                    r.run();
                } catch (Exception e) {
                    Log.e("Timer runnable error!");
                    e.printStackTrace();
                }
                if (!loop) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }

    /**
     * Stops this timer.
     */
    public void stop() {
        stop = true;
    }
}