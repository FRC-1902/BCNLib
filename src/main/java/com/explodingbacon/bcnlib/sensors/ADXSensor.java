package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.PIDSource;
import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * A class that merges the ADXL362 accelerometer with the ADXRS450 gyro, since they are both contained on one board.
 *
 * @author Ryan Shavell
 * @version 2016.2.19
 */

public class ADXSensor implements PIDSource {

    private ADXL362 acc;
    private ADXRS450_Gyro gyro;

    /**
     * Creates an ADXSensor on port.
     *
     * @param aPort The SPI.Port the accelerometer is on.
     * @param gPort The SPI.Port the gyroscope is on.
     */
    public ADXSensor(SPI.Port aPort, SPI.Port gPort) {
        acc = new ADXL362(aPort, Accelerometer.Range.k4G);
        gyro = new ADXRS450_Gyro(gPort);
    }

    /**
     * Creates an ADXSensor on port, and sets the Accelerometer's range to range.
     *
     * @param aPort The SPI.Port the accelerometer is on.
     * @param gPort The SPI.Port the gyroscope is on.
     */
    public ADXSensor(SPI.Port aPort, SPI.Port gPort, Accelerometer.Range range) {
        acc = new ADXL362(aPort, range);
        gyro = new ADXRS450_Gyro(gPort);
    }

    /**
     * Creates an ADXSensor that uses existing instances of the Accelerometer and Gyroscope.
     *
     * @param a The Accelerometer.
     * @param g The Gyroscope.
     */
    public ADXSensor(ADXL362 a, ADXRS450_Gyro g) {
        acc = a;
        gyro = g;
    }

    /**
     * Gets the current angle of the Gyroscope.
     *
     * @return The current angle of the Gyroscope.
     */
    public double getAngle() {
        return gyro.getAngle();
    }

    /**
     * Gets the rate of the Gyroscope.
     *
     * @return The rate of the Gyroscope.
     */
    public double getGyroRate() {
        return gyro.getRate();
    }


    /**
     * Gets the acceleration of a certain Axis of the Accelerometer.
     *
     * @param axis The axis who's acceleration you're getting.
     * @return The acceleration of a certain Axis of the Accelerometer.
     */
    public double getAcceleration(ADXL362.Axes axis) {
        return acc.getAcceleration(axis);
    }

    /**
     * Gets the accelerations of all the Axises.
     *
     * @return The accelerations of all the Axises.
     */
    public ADXL362.AllAxes getAccelerations() {
        return acc.getAccelerations();
    }

    /**
     * Gets the X axis of the Accelerometer.
     *
     * @return The X axis of the Accelerometer.
     */
    public double getX() {
        return acc.getX();
    }

    /**
     * Gets the Y axis of the Accelerometer.
     *
     * @return The Y axis of the Accelerometer.
     */
    public double getY() {
        return acc.getY();
    }

    /**
     * Gets the Z axis of the Accelerometer.
     *
     * @return The Z axis of the Accelerometer.
     */
    public double getZ() {
        return acc.getZ();
    }

    /**
     * Sets the range of accelerometer.
     *
     * @param range The new range of the Accelerometer.
     */
    public void setAccelerometerRange(Accelerometer.Range range) {
        acc.setRange(range);
    }

    @Override
    public double getForPID() {
        return getAngle();
    }

    @Override
    public void reset() {
        gyro.reset();
    }
}
