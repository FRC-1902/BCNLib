package com.explodingbacon.bcnlib.actuators;

import java.lang.reflect.Constructor;

public class GenericMotor implements MotorInterface {

    MotorInterface motor;

    public <T extends MotorInterface> GenericMotor(Class<T> c, int id) {
        Constructor<?>[] constructor = c.getConstructors();
        try {
            this.motor = (MotorInterface) constructor[0].newInstance(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPower(double power) {
        motor.setPower(power);
    }

    @Override
    public double getPower() {
        return motor.getPower();
    }

    @Override
    public void setReversed(boolean reversed) {
        motor.setReversed(reversed);
    }
}
