package com.explodingbacon.bcnlib.utils;

/**
 * A class used for communicating over I2C.
 *
 * @author Ryan Shavell
 * @version 2016.2.6
 */

public class I2C {

    private edu.wpi.first.wpilibj.I2C i2c;

    public I2C(edu.wpi.first.wpilibj.I2C.Port port, int i) {
        i2c = new edu.wpi.first.wpilibj.I2C(port, i);
    }

    /**
     * Sends a String over I2C.
     *
     * @param s The String to be sent.
     */
    public void sendString(String s) {
        sendBytes(s.getBytes());
    }

    /**
     * Sends bytes over I2C.
     *
     * @param bytes The bytes to be sent.
     */
    public synchronized void sendBytes(byte... bytes) {
        i2c.writeBulk(bytes);
        /*
        for (byte b : bytes) {
            i2c.write(0x00, b);
        }
        */
    }

    /**
     * Gets the WPILib I2C object. To be used if functionality that is not available in this class is available in WPI's
     * implementation.
     *
     * @return The WPILib I2C object.
     */
    public edu.wpi.first.wpilibj.I2C getWPII2C() {
        return i2c;
    }
}