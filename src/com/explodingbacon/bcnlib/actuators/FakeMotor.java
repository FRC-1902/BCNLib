package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.Log;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A fake motor object that outputs its power to the Log
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class FakeMotor extends Motor {

    private double power;
    private boolean log = false;

    /**
     * The default, no-argument constructor
     */
    public FakeMotor() {
        super();
    }

    /**
     * A constructor to specify if this FakeMotor should log itself
     * @param log If this FakeMotor should log itself each time it's set or gotten
     */
    public FakeMotor(boolean log) {
        this.log = log;
    }

    @Override
    public double getPower() {
        if (log) Log.i(getName() + " power is \"" + power + "\".");
        return power;
    }

    @Override
    public void setPower(double d) {
        power = d;
        if (log) Log.i(getName() + "'s power has been set to \"" + power + "\".");
        SmartDashboard.putNumber(getName(), getPower());
    }

    @Override
    public void stopMotor() {
        if (log) Log.i(getName() + " has been stopped!");
    }
}