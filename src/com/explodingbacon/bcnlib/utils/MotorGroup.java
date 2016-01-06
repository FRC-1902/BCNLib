package com.explodingbacon.bcnlib.utils;

//Not using an IDE currently because I don't have IntelliJ, so R.I.P. imports

public class MotorGroup {

	public List<Motor> motors = new ArrayList<>();
	public List<Boolean> inverts = new ArrayList<>();
	
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
	
	public void setPower(double d) {
		int index = 0;
		for (Motor m : motors) {
			m.setPower(inverts.get(index) ? -d : d);
			index++;
		}
	}	
}
