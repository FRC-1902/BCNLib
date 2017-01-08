package com.explodingbacon.bcnlib.vision;

import org.opencv.core.Scalar;

/**
 * BCNLib version of OpenCV's Scalar object.
 *
 * @author Ryan Shavell
 * @version 2016.6.16
 */

public abstract class BCNScalar {

    private int v1, v2, v3;

    /**
     * Creates a BCNScalar.
     *
     * @param v1 Value 1.
     * @param v2 Value 2.
     * @param v3 Value 3.
     */
    protected BCNScalar(int v1, int v2, int v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    /**
     * Converts this to an OpenCV Scalar. Mainly for internal use.
     *
     * @return A Scalar version of this.
     */
    protected Scalar toScalar() {
        return new Scalar(v1, v2, v3);
    }

}
