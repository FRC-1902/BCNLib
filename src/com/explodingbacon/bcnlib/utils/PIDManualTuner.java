package com.explodingbacon.bcnlib.utils;

public class PIDManualTuner implements Runnable { //TODO: NetworkTables
    Thread t;
    int kP, kI, kD;
    PIDController c;

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
