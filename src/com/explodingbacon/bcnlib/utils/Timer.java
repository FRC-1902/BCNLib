package com.explodingbacon.bcnlib.utils;

/**
 * A class that lets us set a timer that will be called once (or every) X seconds.
 *
 * @author Ryan Shavell
 * @version 2016.1.18
 */

public class Timer implements Runnable {

    private double seconds;
    private TimerUser user;
    private boolean loop = true;
    private boolean stop = false;
    private Thread thread;

    /**
     * Creates a Timer that calls TimerUser.timer() after X amount of seconds have gone by.
     * @param seconds How many seconds the timer will wait before calling TimerUser.timer().
     * @param user The TimerUser.
     */
    public Timer(double seconds, TimerUser user) {
        this.seconds = seconds;
        this.user = user;
    }

    /**
     * Creates a Timer that calls TimerUser.timer() after X amount of seconds have gone by (possibly multiple times if looping)
     * @param seconds How many seconds the timer will wait before calling TimerUser.timer().
     * @param loop If the timer should keep looping.
     * @param user The TimerUser.
     */
    public Timer(double seconds, boolean loop, TimerUser user) {
        this.seconds = seconds;
        this.loop = loop;
        this.user = user;
    }

    /**
     * Starts the Timer.
     * @return This timer. To be used for method chaining.
     */
    public Timer start() {
        thread = new Thread(this);
        thread.start();
        return this;
    }

    /**
     * The onLoop that makes this timer work. Don't call this.
     */
    @Override
    public void run() {
        while (true) {
            try {
                if (stop) break;
                Thread.sleep((long) seconds * 1000);
                user.timer();
                if (!loop) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
        user.timerStop();
    }

    /**
     * Stops this timer.
     */
    public void stop() {
        stop = true;
    }
}