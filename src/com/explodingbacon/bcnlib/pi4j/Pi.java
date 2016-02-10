package com.explodingbacon.bcnlib.pi4j;

import se.hirt.pi.adafruit.pwm.PWMDevice;

public class Pi {

    private static PWMDevice device;
    private static boolean init = false;

    /**
     * Initializes the Pi.
     */
    public static void init() {
        try {
            device = new PWMDevice();

            device.setPWMFreqency(50); //50 is what the Pi guide said
            init = true;
        } catch (Exception e) {}
    }

    /**
     * Checks if the Pi is initialized.
     * @return If the Pi is initialized.
     */
    public static boolean isInit() {
        return init;
    }

    /**
     * Gets a PWM channel on the Pi's PWMDevice.
     * @param port The port of the channel.
     * @return A PWM channel on the Pi's PWMDevice.
     */
    protected static PWMDevice.PWMChannel getChannel(int port) {
        return device.getChannel(port);
    }
}
