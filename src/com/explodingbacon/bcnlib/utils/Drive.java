package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.actuators.Motor;
import com.explodingbacon.bcnlib.framework.JoystickInterface;

public class Drive {

    private static Motor right, left;
    public static DriveType type;

    public static void configure(Motor r, Motor l, DriveType t) {
        right = r;
        left = l;
        type = t;
    }

    public static void drive(JoystickInterface... joys) {
        drive(1, joys);
    }

    public static void drive(int pow, JoystickInterface... joys) {
        if (type == DriveType.TANK) {
            tankDrive(joys[0], joys[1], pow);
        } else if (type == DriveType.ARCADE) {
            arcadeDrive(joys[0], pow);
        } else if (type == DriveType.MECANUM) {
            //TODO: need mecanum
        }
    }

    public static void tankDrive(double leftPower, double rightPower) {
        tankDrive(leftPower, rightPower, 1);
    }

    public static void tankDrive(double leftPower, double rightPower, int pow) {
        left.setPower(Math.abs(Math.pow(Math.abs(leftPower), pow)) * Utils.sign(leftPower));
        right.setPower(Math.abs(Math.pow(Math.abs(rightPower), pow)) * Utils.sign(rightPower));
    }

    public static void tankDrive(JoystickInterface left, JoystickInterface right, int pow) {
        tankDrive(left.getY(), right.getY(), pow);
    }

    public static void arcadeDrive(JoystickInterface joy, int pow) {
        arcadeDrive(joy.getX(), joy.getY(), pow);
    }

    public static void arcadeDrive(double joyX, double joyY, int pow) {
        tankDrive(joyY - joyX, joyY + joyX, pow);
    }

    public enum DriveType {
        TANK,
        ARCADE,
        MECANUM
    }
}
