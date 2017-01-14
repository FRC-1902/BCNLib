package com.explodingbacon.bcnlib.actuators;

import edu.wpi.first.wpilibj.SpeedController;

//TODO: Make this class work.

public class FilteredMotor extends Motor {
    Thread t;
    static Double target = 0d, curr = 0d;

    public  <T extends SpeedController> FilteredMotor(Class<T> clazz, int channel) {
        super(clazz, channel);
        t = new Thread(filter);
        t.start();
    }

    @Override
    public void setPower(double d) {
        target = d;
    }

    private Runnable filter = () -> {
        while (true) {
            if (target >= 0) {
                if (curr < target) {
                    curr += 0.01;
                } else {
                    curr = target;
                }
            } else {
                if (curr > target) {
                    curr -= 0.01;
                } else {
                    curr = target;
                }
            }

            sc.set(curr);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
