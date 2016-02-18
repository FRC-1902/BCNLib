package com.explodingbacon.bcnlib.actuators;

/**
 * An implementation of SolenoidInterface that controls a DoubleSolenoid.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class DoubleSolenoid implements SolenoidInterface{

    private edu.wpi.first.wpilibj.DoubleSolenoid sol;
    Boolean reversed = false;

    /**
     * Creates a DoubleSolenoid.
     * @param channel1 The "forward" channel
     * @param channel2 The "reversed" channel
     */
    public DoubleSolenoid(int channel1, int channel2) {
        sol = new edu.wpi.first.wpilibj.DoubleSolenoid(channel1, channel2);
    }

    /**
     * Gets the position of the solenoid as a WPI Value
     * @return The position of the solenoid as a WPI Value
     */
    public edu.wpi.first.wpilibj.DoubleSolenoid.Value getAsValue() {
        return sol.get();
    }

    /**
     * Gets the position of the solenoid as a boolean
     * @return The position of the solenoid as a boolean
     */
    public boolean get() {
        return sol.get() == edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
    }

    /**
     * Set the position of the solenoid using a WPI Value
     * @param state The new position of the solenoid
     */
    public void set(edu.wpi.first.wpilibj.DoubleSolenoid.Value state) {
        sol.set(state);
    }

    /**
     * Set the position of the solenoid using a boolean
     * @param state The new position of the solenoid, as a boolean
     */
    public void set(boolean state) {
        sol.set(state ? edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward :
                edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse);
    }

    /**
     * Set this DoubleSolenoid to be reversed
     * @param reversed If this DoubleSolenoid should be reversed
     */
    public void setReversed(Boolean reversed) {
        this.reversed = reversed;
    }
}