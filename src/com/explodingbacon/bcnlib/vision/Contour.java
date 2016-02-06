package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.utils.Utils;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for OpenCV's MatOfPoint and MatOfPoint2f objects.
 *
 * @author Ryan Shavell
 * @version 2016.2.4
 */

public class Contour extends Image {

    public Point coords = null;
    public MatOfPoint mop = null;
    public MatOfPoint2f mop2f = null;

    public Contour(MatOfPoint mop) {
        super(mop);
        this.mop = mop;
    }

    public Contour(MatOfPoint2f mop2f) {
        super(Vision.toMOP(mop2f));
        this.mop = (MatOfPoint) getMat();
        this.mop2f = mop2f;
    }


    /**
     * Gets the X coordinate of this Contour.
     * @return
     */
    public double getX() {
        if (coords  == null) setCoordsVar();
        return coords.x;
    }

    /**
     * Gets the Y coordinate of this Contour.
     * @return
     */
    public double getY() {
        if (coords == null) setCoordsVar();
        return coords.y;
    }

    /**
     * Gets the X coordinate of the middle of this Contour.
     * @return The X coordinate of the middle of this Contour.
     */
    public double getMiddleX() {
        return getX() + (getWidth() / 2);
    }

    /**
     * Gets the Y coordinate of the middle of this Contour.
     * @return The Y coordinate of the middle of this Contour.
     */
    public double getMiddleY() {
        return getY() + (getHeight() / 2);
    }

    /**
     * Gets the bounding Rectangle of this Contour.
     * @return The bounding Rectangle of this Contour.
     */
    public Rectangle getBoundingRectangle() {
        Rect r = Imgproc.boundingRect(getMatOfPoint());
        Point tl = r.tl();
        Point br = r.br();
        return new Rectangle(Utils.round(tl.x), Utils.round(tl.y), Utils.round(br.x - tl.x), Utils.round(br.y - tl.y));
    }

    /**
     * Gets the MatOfPoint.
     * @return The MatOfPoint.
     */
    public MatOfPoint getMatOfPoint() {
        if (mop == null) mop = Vision.toMOP(mop2f);
        return mop;
    }

    /**
     * Gets the MatOfPoint2f.
     * @return The MatOfPoint2f.
     */
    public MatOfPoint2f getMatOfPoint2f() {
        if (mop2f == null) mop2f = Vision.toMOP2f(mop);
        return mop2f;
    }

    /**
     * Approximates the edges of this Contour.
     * @param precision How precise the approximated edges should be. 0.01 is a good number to start with.
     * @return An approximated version of this Contour.
     */
    public Contour approxEdges(double precision) {
        double epsilon = precision * Imgproc.arcLength(getMatOfPoint2f(), true);

        MatOfPoint2f r = new MatOfPoint2f();

        Imgproc.approxPolyDP(getMatOfPoint2f(), r, epsilon, true);

        return new Contour(r);
    }

    /**
     * Sets the Point object that represents this Contour's X and Y values.
     */
    private void setCoordsVar() {
        coords = ((MatOfPoint)m).toArray()[0];
    }

    /**
     * Converts a List of Contours to a List of MatOfPoint.
     * @param cons
     * @return A List of MatOfPoint.
     */
    public static List<MatOfPoint> toMatOfPoint(List<Contour> cons) {
        List<MatOfPoint> mops = new ArrayList<>();
        for (Contour c : cons) {
            mops.add((MatOfPoint)c.getMat());
        }
        return mops;
    }
}
