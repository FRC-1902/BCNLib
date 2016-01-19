package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.actuators.Motor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that lets us group together motors and mass-set their speeds. Specific motors can also be inverted.
 *
 * @author Ryan Shavell
 * @version 2016.1.18
 */

public class MotorGroup implements Motor {

	private List<Motor> motors = new ArrayList<>();
	private List<Boolean> inverts = new ArrayList<>();
    private double power = 0;
    private boolean reversed = false;

    /**
     * Creates a MotorGroup.
     */
    public MotorGroup() {}

    /**
     * Creates a MotorGroup that consists of motorArray's Motors.
     * @param motorArray The Motors to be a part of this MotorGroup.
     */
	public MotorGroup(Motor[] motorArray) {
		for (Motor m : motorArray) {
			motors.add(m);
			inverts.add(false);
		}
	}

    /**
     * Creates a MotorGroup that consists of motorArray's Motors.
     * @param motorArray The Motors to be a part of this MotorGroup.
     * @param invert Values that determine if certain motors in motorArray should have their directions reversed.
     */
	public MotorGroup(Motor[] motorArray, Boolean[] invert) {
		Collections.addAll(motors, motorArray);
		Collections.addAll(inverts, invert);
	}

    /**
     * Adds a Motor to this MotorGroup.
     * @param m The Motor to add.
     * @return This MotorGroup.
     */
    public MotorGroup addMotor(Motor m) { addMotor(m, false); return this; }

    /**
     * Adds a Motor to this MotorGroup.
     * @param m The Motor to add.
     * @param invert Whether the Motor's direction should be reversed.
     * @return This MotorGroup.
     */
    public MotorGroup addMotor(Motor m, boolean invert) {
        motors.add(m);
        inverts.add(invert);
        return this;
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
