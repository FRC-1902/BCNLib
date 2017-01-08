package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * A class that sets up WPILib's IterativeRobot to use BCNLib's RobotCore object.
 *
 * @author Ryan Shavell
 * @version 2016.2.24
 */

public class AbstractRobot extends IterativeRobot {

    private static RobotCore core = null;

    @Override
    public void robotInit() {core.robotInit();}

    @Override
    public void teleopInit() {
        core.enabledInit();
        core.teleopInit();
    }

    @Override
    public void autonomousInit() {
        core.enabledInit();
        core.autonomousInit();
    }

    @Override
    public void testInit() {
        core.enabledInit();
        core.testInit();
    }

    @Override
    public void disabledInit() {
        core.disabledInit();
    }

    @Override
    public void teleopPeriodic() {
        core.enabledPeriodic();
        core.teleopPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
        core.enabledPeriodic();
        core.autonomousPeriodic();
    }

    @Override
    public void testPeriodic() {
        core.enabledPeriodic();
        core.testPeriodic();
    }

    @Override
    public void disabledPeriodic() {
        core.disabledPeriodic();
    }

    /**
     * Sets the RobotCore that provides the code for this robot.
     * @param c The RobotCore.
     */
    public static void setCore(RobotCore c) {
        core = c;
    }
}
