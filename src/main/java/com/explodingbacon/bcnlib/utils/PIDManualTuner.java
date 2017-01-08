package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.framework.PIDController;

/**
 * A way to manually tune PID Loops in a network table (and a GUI that reads/writes from the NetworkTable)
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public class PIDManualTuner implements Runnable { //TODO: NetworkTables
    Thread t;
    int kP, kI, kD;
    PIDController c;

    /**
     * Creates a PIDManualTuner with the given values
     *
     * @param initialP The initial proportional tuning variable
     * @param initialI The initial integral tuning variable
     * @param initialD The initial derivative tuning variable
     * @param controller The PIDController
     */
    public PIDManualTuner(int initialP, int initialI, int initialD, PIDController controller) {
        this.kP = initialP;
        this.kI = initialI;
        this.kD = initialD;
        this.t = new Thread(this);
        this.c = controller;
    }

    @Override
    public void run() {

    }
}
