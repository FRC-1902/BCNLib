package com.explodingbacon.bcnlib.vision;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for OpenCV's MatOfPoint and MatOfPoint2f objects.
 *
 * @author Ryan Shavell
 * @version 2016.3.9
 */

public class Contour extends Image {

    public Point coords = null;
    public MatOfPoint mop = null;
    public MatOfPoint2f mop2f = null;
    protected Rectangle rect;

    /**
     * Creates a Contour from a MatOfPoint.
     *
     * @param mop The MatOfPoint.
     */
    public Contour(MatOfPoint mop) {
        super(mop);
        this.mop = mop;
        init();
    }

    /**
     * Creates a Contour from a MatOfPoint2f.
     *
     * @param mop2f The MatOfPoint2f.
     */
    public Contour(MatOfPoint2f mop2f) {
        super(Vision.toMOP(mop2f));
        this.mop = (MatOfPoint) getMat();
        this.mop2f = mop2f;
        init();
    }

    /**
     * Initializes this Contour's internal variables.
     */
    private void init() {
        Rect r = Imgproc.boundingRect(mop);
        Point tl = r.tl();
        Point br = r.br();
        coords = tl;
        rect = new Rectangle(tl.x, tl.y, br.x - tl.x, br.y - tl.y);
    }

    /**
     * Gets the area of this Image.
     *
     * @return The area of this Image.
     */
    public double getArea() {
        return rect.width * rect.height;
    }

    /**
     * Gets a Rectangle that surrounds this Image.
     *
     * @return A Rectangle that surrounds this Image.
     */
    public Rectangle getBoundingBox() {
        return rect;
    }

    /**
     * Gets the X coordinate of this Contour.
     *
     * @return The X coordinate of this Contour.
     */
    public double getX() {
        return coords.x;
    }

    /**
     * Gets the Y coordinate of this Contour.
     *
     * @return The Y coordinate of this Contour.
     */
    public double getY() {
        return coords.y;
    }

    @Override
    public double getWidth() { return rect.width; }

    @Override
    public double getHeight() {
        return rect.height;
    }

    /**
     * Gets the X coordinate of the middle of this Contour.
     *
     * @return The X coordinate of the middle of this Contour.
     */
    public double getMiddleX() {
        return getX() + (getWidth() / 2);
    }

    /**
     * Gets the Y coordinate of the middle of this Contour.
     *
     * @return The Y coordinate of the middle of this Contour.
     */
    public double getMiddleY() {
        return getY() + (getHeight() / 2);
    }

    /**
     * Checks if this Contour is convex.
     *
     * @return If this Contour is convex.
     */
    public boolean isConvex() {
        return Imgproc.isContourConvex(getMatOfPoint());
    }

    /**
     * Gets the MatOfPoint.
     *
     * @return The MatOfPoint.
     */
    public MatOfPoint getMatOfPoint() {
        if (mop == null) mop = Vision.toMOP(mop2f);
        return mop;
    }

    /**
     * Gets the MatOfPoint2f.
     *
     * @return The MatOfPoint2f.
     */
    public MatOfPoint2f getMatOfPoint2f() {
        if (mop2f == null) mop2f = Vision.toMOP2f(mop);
        return mop2f;
    }

    /**
     * Approximates the edges of this Contour.
     *
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
     * Converts a List of Contours to a List of MatOfPoint.
     *
     * @param cons The Contours to convert.
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
