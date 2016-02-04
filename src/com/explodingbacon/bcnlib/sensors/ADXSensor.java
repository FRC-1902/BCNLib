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
 * @version 2016.1.26
 */

public class ADXSensor implements PIDSource {

    private ADXL362 acc;
    private ADXRS450_Gyro gyro;

    /**
     * Creates an ADXSensor on port.
     *
     * @param port The SPI.Port this sensor is on.
     */
    public ADXSensor(SPI.Port port) {
        acc = new ADXL362(port, Accelerometer.Range.k4G);
        gyro = new ADXRS450_Gyro(port);
    }

    /**
     * Creates an ADXSensor on port, and sets the Accelerometer's range to range.
     *
     * @param port  The SPI.Port this sensor is on.
     * @param range The range you want the Accelerometer to have.
     */
    public ADXSensor(SPI.Port port, Accelerometer.Range range) {
        acc = new ADXL362(port, range);
        gyro = new ADXRS450_Gyro(port);
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
     * Calibrates the Gyroscope.
     */
    public void calibrate() {
        gyro.calibrate();
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
        //TODO: Decide if we're going to implement this
    }
}
