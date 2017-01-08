package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;
import org.opencv.core.*;

/**
 * A Vision utility class. Used for initializing the library and contains some vision-specific utility functions.
 *
 * @author Ryan Shavell
 * @version 2016.6.16
 */

public class Vision {

    private static boolean init = false;

    /**
     * Initializes the Vision Tracking library.
     */
    public static void init() {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Log.i("Vision library initialized!");
            init = true;
        } catch (Exception e) {
            Log.e("Vision library not detected! Vision processing will not be available.");
            e.printStackTrace();
        }
    }

    /**
     * Checks if the Vision Tracking library is initialized.
     *
     * @return If the Vision Tracking library is initialized.
     */
    public static boolean isInit() {
        return init;
    }

    /**
     * Converts a MatOfPoint2f to a MatOfPoint.
     *
     * @param mop2f The MatOfPoint2f to be converted.
     * @return The converted MatOfPoint2f.
     */
    public static MatOfPoint toMOP(MatOfPoint2f mop2f) {
        MatOfPoint mop = new MatOfPoint();
        mop2f.convertTo(mop, CvType.CV_32S);
        return mop;
    }

    /**
     * Converts a MatOfPoint to a MatOfPoint2f.
     *
     * @param mop The MatOfPoint to be converted.
     * @return The converted MatOfPoint.
     */
    public static MatOfPoint2f toMOP2f(MatOfPoint mop) {
        MatOfPoint2f mop2f = new MatOfPoint2f();
        mop.convertTo(mop2f, CvType.CV_32FC2);
        return mop2f;
    }
}
