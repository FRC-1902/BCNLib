package com.explodingbacon.bcnlib.actuators;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * A fake motor object that outputs its power to the Log
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class FakeMotor implements SpeedController {

    private double power;
    private boolean log = false;
    private boolean inverted = false;

    /**
     * The default, no-argument constructor
     */
    public FakeMotor() {
        super();
    }

    /**
     * A constructor to specify if this FakeMotor should log itself
     *
     * @param log If this FakeMotor should log itself each time it's set or gotten
     */
    public FakeMotor(boolean log) {
        this.log = log;
    }

    @Override
    public void set(double v) {
        power = v * (inverted ? -1 : 1);
    }

    @Override
    public double get() {
        return power;
    }

    @Override
    public void setInverted(boolean b) {
        this.inverted = b;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public void disable() {
        set(0);
    }

    @Override
    public void stopMotor() {
        set(0);
    }

    @Override
    public void pidWrite(double v) {
        set(v);
    }
}