package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.PIDSource;
import com.explodingbacon.bcnlib.sensors.BNO055;

/**
 * @author Ryan Shavell
 * @version 2017.1.26
 */
public class BNOGyro implements PIDSource {

    private BNO055 bn;
    private boolean asVector;

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

    public double getHeading() {
        if (asVector) {
            return bn.getVector()[0];
        } else {
            return bn.getHeading();
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
        //TODO: implement
    }
}
