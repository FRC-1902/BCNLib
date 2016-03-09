package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;
import org.opencv.core.*;

/**
 * A Vision utility class. Used for initializing the library and contains some utility functions.
 *
 * @author Ryan Shavell
 * @version 2016.3.8
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

    public static MatOfPoint toMOP(MatOfPoint2f mop2f) {
        MatOfPoint mop = new MatOfPoint();
        mop2f.convertTo(mop, CvType.CV_32S);
        return mop;
    }

    public static MatOfPoint2f toMOP2f(MatOfPoint mop) {
        MatOfPoint2f mop2f = new MatOfPoint2f();
        mop.convertTo(mop2f, CvType.CV_32FC2);
        return mop2f;
    }
}
