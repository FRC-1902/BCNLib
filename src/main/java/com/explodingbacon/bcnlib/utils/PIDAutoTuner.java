package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.framework.PIDController;
import edu.wpi.first.wpilibj.Joystick;

/**
 * A class that attempts to auto-tune your PID controllers. This is very much a work in progress and requires careful
 * supervision and a human eye to pull the plug and make manual adjustments if and when things get out of hand. Currently
 * UNFINISHED.
 * <p><p>
 * USAGE: Create the object and call start. Hold joystick button 1. ExtendableRobot will run for 5 seconds. When the robot stops,
 * release the button and reset any mechanism related to the PID onLoop. Repeat until the console shows a set of PID values.
 *
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public class PIDAutoTuner implements Runnable {
    PIDController c;
    double initialkP = 1, initialkI = 1, initialkD = 1;
    double kP, kI, kD, error, errorSum = 0, aggression;
    long startTime;
    int signChanges = 0, goodLoops = 0, state = 0, target;
    Thread t;
    Boolean sign = true;
    Joystick joy;

    final int FIRST_MAGIC_PID_CONSTANT = 25;
    final int SECOND_MAGIC_PID_CONSTANT = 20;

    /**
     * @param controller The PID Controller to tune
     * @param target     The encoder target to use for the PID controller
     * @param scalar     Initial scalar for PID values. Should be a power of 10.
     * @param aggression How "aggressive" the PID tuner will be. Smaller numbers take more time but are more precise
     * @param joystick   The joystick to use for tuning.
     */
    public PIDAutoTuner(PIDController controller, int target, double scalar, double aggression, Joystick joystick) {
        this.c = controller;
        this.initialkP *= scalar;
        this.initialkI *= scalar;
        this.initialkD *= scalar;

        this.kP = initialkP;
        this.kI = 0;
        this.kD = 0;
        this.target = target;
        this.aggression = aggression;
        this.joy = joystick;
        t = new Thread(this);
    }

    /**
     * Start this PIDAutoTuner
     */
    public void start() {
        t.start();
    }

    private void reset() {
        goodLoops = 0;
        sign = true;
        signChanges = 0;

    }

    @Override
    public void run() {
        c.reTune(kP, kI, kD);
        c.setTarget(target);
        c.enable();

        startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() < startTime + 5000 && joy.getRawButton(1)) {
            error = c.getCurrentError();

            if (Math.abs(error) <= 0.05 * target) {
                goodLoops += 1;
            } else if (((sign && error < 0) || (!sign && error >= 0))) {
                signChanges++;
                sign = error > 0;
            }

            errorSum += error;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        c.disable();

        switch (state) {
            case 0: //Tuning kP
                //If we spend a significant amount of time below the target
                if (error >= target * FIRST_MAGIC_PID_CONSTANT) {
                    kP *= aggression;
                } else {
                    kI = initialkI;
                    reset();
                    kP /= 2;
                    state = 1;
                }
                break;

            case 1:
                if (goodLoops <= SECOND_MAGIC_PID_CONSTANT) {
                    kI *= aggression;
                } else {
                    kD = initialkD;
                    reset();
                    state = 2;
                }
                break;

            case 2: //TODO: Derivative term tuning
                state = 3;
                break;

            case 3:
                System.out.printf("PID Values: kP=%.3f, kI=%.3f, d=%.3f", kP, kI, kD);
        }

        //Loop until someone pushes the joystick button, signifying that they have reset the robot
        while (!joy.getRawButton(1)) {
        }
    }
}
