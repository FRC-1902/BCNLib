package com.explodingbacon.bcnlib.framework;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 */
public abstract class Command implements Runnable {
    private Thread t;
    public Subsystem requiredSub;
    private Boolean finishedExecution = false;
    private Boolean isRunning = false;
    private Boolean cancel = false;

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
        finishedExecution = false;
        t.start();
    }

    /**
     * Blocks until the command has finished execution. Note that this method will not return until the <code>onStop</code>
     * method has returned.
     */
    public void waitTillFinished() {
        while(!finishedExecution) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the command is currently running.
     * @return If the command is currently running.
     */
    public boolean isRunning(){
        return isRunning;
    }

    /**
     * Forces this command to onStop running.
     */
    public void forceStop() {
        cancel = true;
    }

    /**
     * Runs once every time the <code>Command</code> is started.
     */
    public abstract void onInit();

    /**
     * Runs continuously until <code>isFinished</code> returns true. Is not guaranteed to run.
     */
    public abstract void onLoop();

    /**
     * Runs once when <code>isFinished</code> returns true and <code>onLoop</code> finishes.
     */
    public abstract void onStop();

    /**
     * User-implemented method to check if <code>onLoop</code> should onStop executing.
     *
     * @return True when <code>onLoop</code> should onStop executing
     */
    public abstract boolean isFinished();

    /**
     * Manages the lifecycle of the <code>Command</code>
     */
    @Override
    public void run() {
        isRunning = true;
        if (requiredSub != null)
            requiredSub.takeControl(this);

        onInit();
        while (!isFinished() && !cancel) {
            onLoop();
        }
        cancel = false;
        onStop();

        if (requiredSub != null)
            requiredSub.releaseControl(this);

        finishedExecution = true;
        isRunning = false;
    }
}
