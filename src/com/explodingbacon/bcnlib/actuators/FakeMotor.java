package com.explodingbacon.bcnlib.actuators;

/**
 * @author Ryan Shavell
 * @version 2016.1.26
 */

public class FakeMotor extends Motor {

    private double power;
    private boolean log = false;

    public FakeMotor() {
        super();
    }

    public FakeMotor(boolean log) {
        this.log = log;
    }

    @Override
    public double getPower() {
        if (log) System.out.println(getSBKey() + " power is \"" + power + "\".");
        return power;
    }

    @Override
    public void setPower(double d) {
        power = d;
        if (log) System.out.println(getSBKey() + "'s power has been set to \"" + power + "\".");
    }
}