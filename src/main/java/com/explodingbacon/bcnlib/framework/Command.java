package com.explodingbacon.bcnlib.framework;

/**
 * @author Dominic Canora
 * @version 2017.1.26
 */
public abstract class Command implements Runnable {
    private Thread t;
    private Boolean finishedExecution = false;
    private Boolean isRunning = false;
    private Boolean cancel = false;

    //I am like the only person that uses slash slash

    /**
     * Creates a new <code>Command</code>.
     */
    public Command() {
        this.t = new Thread(this);
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
        while (!finishedExecution) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if this Command is currently running.
     *
     * @return If this Command is currently running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Forces this Command to stop running.
     */
    public void stop() {
        cancel = true;
    }

    public void forceStop() {
        cancel = true;
        t.interrupt();
        finish();
    }

    /**
     * Runs once every time this <code>Command</code> is started.
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
     * User-implemented method to check if <code>onLoop</code> should stop executing.
     *
     * @return True when <code>onLoop</code> should onStop executing
     */
    public abstract boolean isFinished();

    /**
     * Manages the lifecycle of this <code>Command</code>
     */
    @Override
    public void run() {
        isRunning = true;

        onInit();
        while (!isFinished() && !cancel) {
            onLoop();

            try {
                Thread.sleep(20); //was 10
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cancel = false;
        onStop();

        finish();
    }

    private void finish() {
        isRunning = false;
    }
}
