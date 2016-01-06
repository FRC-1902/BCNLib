package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.actuators.Motor;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that lets us group together motors and mass-set their speeds. Specific motors can also be inverted.
 *
 * @author Ryan Shavell
 * @version 2016.1.6
 */

public class MotorGroup implements Motor {

	private List<Motor> motors = new ArrayList<>();
	private List<Boolean> inverts = new ArrayList<>();
    private double power = 0;
    private boolean reversed = false;
	
	public MotorGroup(Motor[] array) {
		for (Motor m : array) {
			motors.add(m);
			inverts.add(false);
		}
	}
	
	public MotorGroup(Motor[] array, Boolean[] invert) {
		for (Motor m : array) {
			motors.add(m);
		}
		for (Boolean b : invert) {
			inverts.add(b);
		}
	}

    @Override
	public void setPower(double d) {
		int index = 0;
		for (Motor m : motors) {
            double speed = inverts.get(index) ? -d : d;
			m.setPower(reversed ? -speed : speed);
			index++;
		}
        power = d;
	}

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    /**
     * Gets all the motors in this MotorGroup.
     * @return All the motors in this MotorGroup.
     */
    public List<Motor> getMotors() {
        return new ArrayList<>(motors);
    }
}
