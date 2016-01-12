package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The main class of the robot. This takes care of creating, initializing, and running all other classes. MAKE SURE you
 * call super when you override a method, or else the entire framework will break.
 *
 * @author Ryan Shavell
 * @version 2016.1.10
 */

public abstract class Robot extends IterativeRobot {

    private static Robot self;

    public Robot() {
        self = this;
    }

    /**
     * Gets whether the robot is enabled.
     * @return Whether the robot is enabled or not.
     */
    public static boolean getEnabled() {
        return self.isEnabled();
    }

    /**
     * Gets the mode the robot is currently in.
      * @return The mode the robot is currently in.
     */
    public static Mode getMode() {
        if (self.isAutonomous()) {
            return Mode.AUTONOMOUS;
        } else if (self.isOperatorControl()) {
            return Mode.TELEOP;
        } else if (self.isTest()) {
            return Mode.TEST;
        } else {
            return Mode.NONE;
        }
    }

    /**
     * Gets the current voltage of the robot's battery.
     * @return The current voltage of the robot's battery.
     */
    public static double getBatteryVoltage() {
        return self.m_ds.getBatteryVoltage();
    }

    /**
     * Gets the (approximate) time into the match.
     * @return The (approximate) time into the match.
     */
    public static double getMatchTime() {
        return self.m_ds.getMatchTime();
    }

    /**
     * Gets whether the Driver Station is attached.
     * @return Whether the Driver Station is attached.
     */
    public static boolean isDSAttached() {
        return self.m_ds.isDSAttached();
    }

    /**
     * Gets the alliance our robot is on.
     * @return The alliance our robot is on.
     */
    public static Alliance getAlliance() {
        DriverStation.Alliance a = self.m_ds.getAlliance();
        if (a == DriverStation.Alliance.Blue) {
            return Alliance.BLUE;
        } else if (a == DriverStation.Alliance.Red) {
            return Alliance.RED;
        } else {
            return Alliance.NONE;
        }
    }

    /**
     * Gets the alliance station our robot is at.
     * @return The alliance station our robot is at.
     */
    public static int getAllianceStation() {
        return self.m_ds.getLocation();
    }

    /**
     * Gets the Robot object.
     * @return The Robot object.
     */
    public static Robot getRobot() { return self; }

    public enum Mode {
        AUTONOMOUS,
        TELEOP,
        TEST,

        NONE
    }

    public enum Alliance {
        BLUE,
        RED,
        NONE
    }

}
