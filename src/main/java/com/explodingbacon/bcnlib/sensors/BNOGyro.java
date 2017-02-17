package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.PIDSource;
import com.explodingbacon.bcnlib.sensors.BNO055;

/**
 * @author Ryan Shavell
 * @version 2017.2.16
 */
public class BNOGyro implements PIDSource {

    private BNO055 bn;
    private boolean asVector;

    private double fakeZero = 0;

    public BNOGyro(boolean asVector) {
        bn = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_IMUPLUS, BNO055.vector_type_t.VECTOR_EULER);
        this.asVector = asVector;
    }

    public boolean isPresent() {
        return bn.isSensorPresent();
    }

    public boolean isCalibrated() {
        return bn.isCalibrated();
    }

    //TODO: test
    public void rezero() {
        setZero(getForPID());
    }

    public void setZero(double newZero) {
        fakeZero = newZero;
    }

    public double getHeading() {
        if (asVector) {
            //TODO: test
            double vector = bn.getVector()[0];
            vector -= fakeZero;
            if (vector < 0) {
                vector = 360 + vector;
            }
            return vector;
        } else {
            //TODO: test
            return bn.getHeading() - fakeZero;
        }
    }

    public double getRoll() {
        return bn.getVector()[1];
    }

    public double getPitch() {
        return bn.getVector()[2];
    }

    @Override
    public double getForPID() {
        return getHeading();
    }

    @Override
    public void reset() {
        //TODO: test
        rezero();
    }
}
