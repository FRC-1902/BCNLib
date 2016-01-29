package com.explodingbacon.bcnlib.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for OpenCV's Mat object.
 *
 * @author Ryan Shavell
 * @version 2016.1.28
 */

public class Image {

    private Mat m;

    protected Image() {
        m = new Mat();
    }

    protected Image(Mat m) {
        this.m = m;
    }

    /**
     * Gets the width of this Image.
     *
     * @return The width of this Image.
     */
    public int getWidth() { return m.width(); }

    /**
     * Gets the height of this Image.
     *
     * @return The height of this Image.
     */
    public int getHeight() {
        return m.height();
    }

    /**
     * Gets the Contours in this Image.
     *
     * @return The Contours in this Image.
     */
    public Contours getContours() {
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(m, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        return new Contours(contours);
    }

    /**
     * Sets the color range of this Image. Anything outside this range becomes black.
     *
     * @param low  The lowest acceptable color.
     * @param high The highest acceptable color.
     */
    public void setColorRange(Color low, Color high) {
        m.colRange(VisUtil.toBGR(low), VisUtil.toBGR(high));
    }

    /**
     * Creates an identical copy of this Image.
     *
     * @return An identical copy of this Image.
     */
    public Image copy() {
        Mat copy = new Mat();
        m.copyTo(copy);
        return new Image(copy);
    }

    /**
     * Draws a Rectangle on this Image.
     *
     * @param x  The X coordinate of the Rectangle.
     * @param y  The Y coordinate of the Rectangle.
     * @param x2 The second X coordinate of the Rectangle.
     * @param y2 The second Y coordinate of the Rectangle.
     * @param c  The Color of the Rectangle.
     */
    public void drawRectangle(int x, int y, int x2, int y2, Color c) {
        Imgproc.rectangle(m, new Point(x, y), new Point(x2, y2), VisUtil.toScalar(c));
    }

    /**
     * Draws Contours onto this Image.
     *
     * @param con The Contours to be drawn.
     * @param c   The Color of the Contours.
     */
    public void drawContours(Contours con, Color c) {
        Imgproc.drawContours(m, con.contours, -1, VisUtil.toScalar(c));
    }

    /**
     * Draws an arrow on this Image.
     *
     * @param x  The X coordinate of the arrow.
     * @param y  The Y coordinate of the arrow.
     * @param x2 The second X coordinate of the arrow.
     * @param y2 The second Y coordinate of the arrow.
     * @param c  The Color of the arrow.
     */
    public void drawArrow(double x, double y, double x2, double y2, Color c) {
        Imgproc.arrowedLine(m, new Point(x, y), new Point(x2, y2), VisUtil.toScalar(c));
    }

    /**
     * Saves this Image as a file.
     *
     * @param fileName The name of the file.
     */
    public void saveAs(String fileName) {
        Imgcodecs.imwrite(fileName, m);
    }

    /**
     * Gets the internal Map of this Image. Should be used when you need functionality not provided by BCNLib, but available in OpenCV.
     *
     * @return The internal Map of this Image.
     */
    public Mat getMat() {
        return m;
    }

    /**
     * Sets the internal Map of this Image.
     *
     * @param newMat The new Map to be used.
     */
    public void setMat(Mat newMat) {
        m = newMat;
    }

    /**
     * Creates an Image from an existing image file.
     *
     * @param fileName The name of the image file.
     * @return The new Image.
     */
    public static Image fromFile(String fileName) {
        return new Image(Imgcodecs.imread(fileName));
    }
}
