package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;
import org.opencv.core.*;

import java.awt.*;

/**
 * A Vision utility class. Used for initializing the library and contains functions for converting between normal Java
 * objects and OpenCV's weird stuff (i.e. Color -> Scalar, RGB -> BGR)
 *
 * @author Ryan Shavell
 * @version 2016.2.6
 */

public class Vision {

    private static boolean init = false;

    /**
     * Initializes the Vision Tracking library.
     */
    public static void init() {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError e) {
            Log.e("Vision library not detected! Vision processing will not be available.");
        }
        init = true;
    }

    /**
     * Checks if the Vision Tracking library is initialized.
     * @return If the Vision Tracking library is initialized.
     */
    public static boolean isInitialized() {
        return init;
    }

    /**
     * Converts a Color to a BGR int.
     * @param c The Color to convert
     * @return A BGR int.
     */
    public static int toBGR(Color c) {
        return toBGR(c.getRGB());
    }

    /**
     * Converts a RGB int to a BGR int.
     * @param rgb The RGB int to convert.
     * @return A BGR int.
     */
    public static int toBGR(int rgb) {
        int in = rgb;
        int red = (in >> 16) & 0xFF;
        int green = (in >> 8) & 0xFF;
        int blue = (in >> 0) & 0xFF;
        int out = (blue << 16) | (green << 8) | (red << 0);
        return out;
    }

    /**
     * Converts a Color to a Scalar (a class similar to Color that uses BGR instead of RGB)
     * @param c The Color to convert.
     * @return A Scalar.
     */
    public static Scalar toScalar(Color c) {
        return new Scalar(c.getBlue(), c.getGreen(), c.getRed());
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
