package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.Log;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Ryan Shavell
 * @version 2016.2.13
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
        if (log) Log.i(getSBKey() + " power is \"" + power + "\".");
        return power;
    }

    @Override
    public void setPower(double d) {
        power = d;
        if (log) Log.i(getSBKey() + "'s power has been set to \"" + power + "\".");
        SmartDashboard.putNumber(getSBKey(), getPower());
    }

    @Override
    public void stopMotor() {
        if (log) Log.i(getSBKey() + " has been stopped!");
    }
}