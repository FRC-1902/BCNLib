package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.actuators.Motor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for any BCNLIb Robot. Currently supported robot controllers: roboRIOs and Raspberry Pis
 *
 * @author Ryan Shavell
 * @version 2016.3.24
 */

public abstract class RobotCore {

    //RIO variables
    private static IterativeRobot rio = null;
    public static DriverStation ds = null;

    public static List<Subsystem> subsystems = new ArrayList<>();
    private static RobotCore self;

    /**
     * Creates a RobotCore for a roboRIO-based robot.
     * @param r The IterativeRobot class of the robot.
     */
    public RobotCore(IterativeRobot r) {
        rio = r;
        ds = DriverStation.getInstance();
        self = this;
    }

    /**
     * Runs when the robot starts.
     */
    public void robotInit() {}

    /**
     * Runs when the Robot is enabled, regardless of what mode it is in.
     */
    public void enabledInit() {
        subsystems.forEach(Subsystem::enabledInit);
    }

    /**
     * Runs when teleoperated mode starts.
     */
    public void teleopInit() {
        //Log.d("Teleop init!");
    }

    /**
     * Runs when autonomous mode starts.
     */
    public void autonomousInit() {
        //Log.d("Autonomous init!");
    }

    /**
     * Runs when test mode starts.
     */
    public void testInit() {
        //Log.d("Test init!");
    }

    /**
     * Runs when the Robot is disabled.
     */
    public void disabledInit() {
        //Log.d("Disabled init!");
        subsystems.forEach(Subsystem::disabledInit);
        for (Motor m : Motor.getAllMotors()) {
            m.setUser(null);
        }
        Log.d("Set all Motors users to null!");
    }

    /**
     * Loops while the Robot is enabled.
     */
    public void enabledPeriodic() {}

    /**
     * Loops while in teleop mode.
     */
    public void teleopPeriodic() {}

    /**
     * Loops while in autonomous mode.
     */
    public void autonomousPeriodic() {}

    /**
     * Loops while in test mode.
     */
    public void testPeriodic() {}

    /**
     * Loops while the Robot is disabled.
     */
    public void disabledPeriodic() {}

    /**
     * Checks if this robot is controlled a roboRIO.
     * @return If this robot is controlled by a roboRIO.
     */
    public static boolean isRIO() {
        return rio != null;
    }

    /**
     * Checks if this robot is contolled by a Raspberry Pi.
     * @return If this robot is contolled by a Raspberry Pi.
     */
    public static boolean isPi() {
        return rio == null;
    }

    /**
     * Checks whether the robot is enabled.
     * @return Whether the robot is enabled or not.
     */
    public static boolean isEnabled() {
        if (isRIO()) {
            return rio.isEnabled();
        } else {
            return false;
        }
    }

    /**
     * Gets the mode the robot is currently in.
     * @return The mode the robot is currently in.
     */
    public static Mode getMode() {
        if (isRIO()) {
            if (rio.isAutonomous()) {
                return Mode.AUTONOMOUS;
            } else if (rio.isOperatorControl()) {
                return Mode.TELEOP;
            } else if (rio.isTest()) {
                return Mode.TEST;
            } else {
                return Mode.NONE;
            }
        } else {
            return Mode.NONE;
        }
    }

    /**
     * Checks if this Robot is in Teleop mode.
     *
     * @return If this Robot is in Teleop mode.
     */
    public static boolean isTeleop() {
        return getMode() == Mode.TELEOP;
    }

    /**
     * Checks if this Robot is in Autonomous mode.
     *
     * @return If this Robot is in Autonomous mode.
     */
    public static boolean isAutonomous() {
        return getMode() == Mode.AUTONOMOUS;
    }

    /**
     * Checks if this Robot is in Test mode.
     *
     * @return If this Robot is in Test mode.
     */
    public static boolean isTest() {
        return getMode() == Mode.TEST;
    }

    /**
     * Gets the current voltage of the robot's battery.
     * @return The current voltage of the robot's battery.
     */
    public static double getBatteryVoltage() {
        if (isRIO()) {
            return ds.getBatteryVoltage();
        } else {
            return 9001; //TODO: Implement?
        }
    }

    /**
     * Gets the (approximate) time into the match.
     * @return The (approximate) time into the match.
     */
    public static double getMatchTime() {
        if (isRIO()) {
            return ds.getMatchTime();
        } else {
            return -1; //TODO: Implement?
        }
    }

    /**
     * Gets whether the Driver Station is attached.
     * @return Whether the Driver Station is attached.
     */
    public static boolean isDSAttached() {
        if (isRIO()) {
            return ds.isDSAttached();
        } else {
            return false; //TODO: Implement
        }
    }

    /**
     * Gets the Driver Station.
     * @return The Driver Station.
     */
    public static DriverStation getDS() {
        if (isRIO()) {
            return ds;
        } else {
            Log.e("Attempted to get the WPI Driver station while in Raspberry Pi mode!");
            return null;
        }
    }

    /**
     * Gets the alliance our robot is on.
     * @return The alliance our robot is on.
     */
    public static Alliance getAlliance() {
        if (isRIO()) {
            DriverStation.Alliance a = ds.getAlliance();
            if (a == DriverStation.Alliance.Blue) {
                return Alliance.BLUE;
            } else if (a == DriverStation.Alliance.Red) {
                return Alliance.RED;
            } else {
                return Alliance.NONE;
            }
        } else {
            return Alliance.NONE;
        }
    }

    /**
     * Gets the alliance station our robot is at.
     * @return The alliance station our robot is at.
     */
    public static int getAllianceStation() {
        if (isRIO()) {
            return ds.getLocation();
        } else {
            return -1;
        }
    }

    /**
     * Gets the RobotCore object.
     * @return The RobotCore object.
     */
    public static RobotCore self() { return self; }

    /**
     * An enum that represents the different alliances this Robot can be on.
     */
    public enum Alliance {
        BLUE,
        RED,

        NONE
    }
}
