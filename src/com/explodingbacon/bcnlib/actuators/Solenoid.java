package com.explodingbacon.bcnlib.actuators;

public class Solenoid implements SolenoidInterface {

    private edu.wpi.first.wpilibj.Solenoid sol;

    public Solenoid(int channel) {
        sol = new edu.wpi.first.wpilibj.Solenoid(channel);
    }

    @Override
    public boolean get() {
        return sol.get();
    }

    @Override
    public void set(boolean state) {
        sol.set(state);
    }
}