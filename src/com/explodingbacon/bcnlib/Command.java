package com.explodingbacon.bcnlib;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 */
public abstract class Command implements Runnable {
    Thread t;
    Subsystem requiredSub;

    /**
     * Creates a new <code>Command</code>
     */
    public Command() {
        this.t = new Thread(this);
    }

    /**
     * Specify that this <code>Command</code> needs exclusive control of a <code>Subsystem</code> to run correctly
     *
     * @param subsystem <code>Subsystem</code> that this <code>Command</code> requires
     */
    public void requires(Subsystem subsystem) {
        this.requiredSub = subsystem;
    }

    /**
     * Called to start the <code>Command</code>
     */
    public void start() {
        if (t == null) {
            throw new RuntimeException("Attempted to start un-initialized Command. Are you calling super() in the " +
                    "Command's constructor?");
        }
        t.start();
    }

    /**
     * Runs once every time the <code>Command</code> is started.
     */
    public abstract void init();

    /**
     * Runs continuously until <code>isFinished</code> returns true. Is not guaranteed to run.
     */
    public abstract void loop();

    /**
     * Runs once when <code>isFinished</code> returns true and <code>loop</code> finishes.
     */
    public abstract void stop();

    /**
     * User-implemented method to check if <code>loop</code> should stop executing.
     *
     * @return True when <code>loop</code> should stop executing
     */
    public abstract boolean isFinished();

    /**
     * Manages the lifecycle of the <code>Command</code>
     */
    @Override
    public void run() {
        if (requiredSub != null)
            requiredSub.takeControl(this);

        init();
        while (!isFinished()) {
            loop();
        }
        stop();

        if (requiredSub != null)
            requiredSub.releaseControl(this);
    }
}
