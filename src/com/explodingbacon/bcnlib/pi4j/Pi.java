package com.explodingbacon.bcnlib.pi4j;

import com.explodingbacon.bcnlib.framework.Mode;
import com.explodingbacon.bcnlib.framework.RobotCore;
import com.explodingbacon.bcnlib.utils.CodeThread;
import com.pi4j.io.gpio.*;
import se.hirt.pi.adafruit.pwm.PWMDevice;

/**
 * A class for managing a RaspberryPi as a robot controller.
 *
 * @author Ryan Shavell
 * @version 2016.2.16
 */

public class Pi {

    private static PWMDevice device;
    private static GpioController gpio;
    private static boolean init = false;
    private static CodeThread thread = null;
    private static RobotCore core;

    private static boolean didRobotInit = false;
    private static boolean wasEnabled = false;
    private static Mode previousMode = Mode.NONE;

    /**
     * Initializes the Pi.
     */
    public static void init(RobotCore c) {
        core = c;
        try {
            device = new PWMDevice();

            device.setPWMFreqency(50); //50 is what the Pi guide said

            gpio = GpioFactory.getInstance();

            thread = new CodeThread(Pi::threadPeriodic);

            thread.start();

            init = true;
        } catch (Exception e) {}
    }

    /**
     * The code that is periodically called in the update thread.
     */
    private static void threadPeriodic() {
        if (!didRobotInit) {
            core.robotInit();
            didRobotInit = true;
        }
        Mode m = Pi.getMode();
        if (m == Mode.AUTONOMOUS) {
            if (previousMode != Mode.AUTONOMOUS) core.autonomousInit();
            core.autonomousPeriodic();
        } else if (m == Mode.TELEOP) {
            if (previousMode != Mode.TELEOP) core.teleopInit();
            core.teleopPeriodic();
        } else if (m == Mode.TEST) {
            if (previousMode != Mode.TEST) core.testInit();
            core.testPeriodic();
        }
        boolean enabled = Pi.isEnabled();
        if (enabled) {
            if (!wasEnabled) core.enabledInit();
        } else {
            if (wasEnabled) core.disabledInit();
            core.disabledPeriodic();
        }
        wasEnabled = enabled;
    }

    /**
     * Checks if the Pi is initialized.
     * @return If the Pi is initialized.
     */
    public static boolean isInit() {
        return init;
    }

    /**
     * Checks if the Pi is enabled on the Driver Station.
     * @return If the Pi is enabled on the Driver Station.
     */
    public static boolean isEnabled() {
        return false; //TODO: Pull the enabled state from the online driver station
    }

    /**
     * Gets the current mode of the Pi.
     * @retur The current mode of the Pi.
     */
    public static Mode getMode() { //TODO: Pull the current mode from the online driver station
        return Mode.TELEOP;
    }

    /**
     * Gets a PWM channel on the Pi's PWMDevice.
     * @param port The port of the channel.
     * @return A PWM channel on the Pi's PWMDevice.
     */
    protected static PWMDevice.PWMChannel getChannel(int port) {
        return device.getChannel(port);
    }

    /**
     * Gets a GPIO Pin on the Pi.
     * @param channel The channel of the GPIO Pin.
     * @return The GPIO Pin.
     */
    protected static GpioPinDigitalOutput getGPIOPin(int channel) {
        return gpio.provisionDigitalOutputPin(intToPin(channel), "Solenoid_" + channel, PinState.HIGH);
    }

    /**
     * Converts an int to a Pin. TODO: Search until the end of time to find a better way to do this
     * @param channel The int to be converted. Valid values are 0-29.
     * @return The Pin.
     */
    protected static Pin intToPin(int channel) {
        switch(channel) {
            case 0:
                return RaspiPin.GPIO_00;
            case 1:
                return RaspiPin.GPIO_01;
            case 2:
                return RaspiPin.GPIO_02;
            case 3:
                return RaspiPin.GPIO_03;
            case 4:
                return RaspiPin.GPIO_04;
            case 5:
                return RaspiPin.GPIO_05;
            case 6:
                return RaspiPin.GPIO_06;
            case 7:
                return RaspiPin.GPIO_07;
            case 8:
                return RaspiPin.GPIO_08;
            case 9:
                return RaspiPin.GPIO_09;
            case 10:
                return RaspiPin.GPIO_10;
            case 11:
                return RaspiPin.GPIO_11;
            case 12:
                return RaspiPin.GPIO_12;
            case 13:
                return RaspiPin.GPIO_13;
            case 14:
                return RaspiPin.GPIO_14;
            case 15:
                return RaspiPin.GPIO_15;
            case 16:
                return RaspiPin.GPIO_16;
            case 17:
                return RaspiPin.GPIO_17;
            case 18:
                return RaspiPin.GPIO_18;
            case 19:
                return RaspiPin.GPIO_19;
            case 20:
                return RaspiPin.GPIO_20;
            case 21:
                return RaspiPin.GPIO_21;
            case 22:
                return RaspiPin.GPIO_22;
            case 23:
                return RaspiPin.GPIO_23;
            case 24:
                return RaspiPin.GPIO_24;
            case 25:
                return RaspiPin.GPIO_25;
            case 26:
                return RaspiPin.GPIO_26;
            case 27:
                return RaspiPin.GPIO_27;
            case 28:
                return RaspiPin.GPIO_28;
            case 29:
                return RaspiPin.GPIO_29;
        }
        return null;
    }
}
