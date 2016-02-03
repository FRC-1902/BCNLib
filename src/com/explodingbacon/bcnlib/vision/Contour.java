package com.explodingbacon.bcnlib.vision;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for OpenCV's MatOfPoint object.
 *
 * @author Ryan Shavell
 * @version 2016.2.3
 */

public class Contour extends Image {

    public Point coords = null;

    public Contour(MatOfPoint mop) {
        super(mop);
    }

    /**
     * Gets the X coordinate of this Contour relative to the Image it came from.
     * @return
     */
    public double getX() {
        if (coords  == null) initCoords();
        return coords.x;
    }

    /**
     * Gets the Y coordinate of this Contour relative to the Image it came from.
     * @return
     */
    public double getY() {
        if (coords == null) initCoords();
        return coords.y;
    }

    /**
     * Gets the Point object that represents this Contour's X and Y values.
     */
    private void initCoords() {
        coords = ((MatOfPoint)m).toArray()[0];
    }

    /**
     * Converts a List of Contours to a List of MatOfPoint.
     * @param cons
     * @return
     */
    public static List<MatOfPoint> toMatOfPoint(List<Contour> cons) {
        List<MatOfPoint> mops = new ArrayList<>();
        for (Contour c : cons) {
            mops.add((MatOfPoint)c.getMat());
        }
        return mops;
    }
}
