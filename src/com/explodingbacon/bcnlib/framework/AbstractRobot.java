package com.explodingbacon.bcnlib.framework;

import edu.wpi.first.wpilibj.IterativeRobot;

public class AbstractRobot extends IterativeRobot {

    private static RobotCore core = null;

    @Override
    public void robotInit() {core.robotInit();}

    @Override
    public void teleopInit() {
        core.teleopInit();
    }

    @Override
    public void autonomousInit() {
        core.autonomousInit();
    }

    @Override
    public void testInit() {
        core.testInit();
    }

    @Override
    public void disabledInit() {
        core.disabledInit();
    }

    @Override
    public void teleopPeriodic() {
        core.teleopPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
        core.autonomousPeriodic();
    }

    @Override
    public void testPeriodic() {
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
