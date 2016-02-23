package com.explodingbacon.bcnlib.vision;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for OpenCV's Mat object.
 *
 * @author Ryan Shavell
 * @version 2016.2.21
 */

public class Image {

    protected Mat m;

    public Image() {
        m = new Mat();
    }

    public Image(Mat m) {
        this.m = m;
    }

    /**
     * Gets the width of this Image.
     *
     * @return The width of this Image.
     */
    public double getWidth() {
        return m.width();
    }

    /**
     * Gets the height of this Image.
     *
     * @return The height of this Image.
     */
    public double getHeight() {
        return m.height();
    }

    /**
     * Gets the Contours in this Image.
     *
     * @return The Contours in this Image.
     */
    public List<Contour> getContours() {
        List<Contour> result = new ArrayList<>();
        List<MatOfPoint> contours = new ArrayList<>();
        Mat copy = new Mat();
        m.copyTo(copy);
        Imgproc.findContours(copy, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        for (MatOfPoint mop : contours) {
            result.add(new Contour(mop));
        }
        return result;
    }

    /**
     * Gets all the human faces in this Image. TODO: Do not return an OpenCV class, return a BCNLib equivalent (Normal rectangles?)
     * @return All the human faces in this Image.
     */
    public Rect[] getFaces() {
        //TODO: Fix the resource path to the xml file
        CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(m, faceDetections);

        return faceDetections.toArray();
    }

    /**
     * Sets the color range of this Image. Anything outside this range becomes black.
     *
     * @param low  The lowest acceptable color.
     * @param high The highest acceptable color.
     */
    public Image colorRange(Color low, Color high) {
        Mat newM = new Mat();
        Core.inRange(m, low.toScalar(), high.toScalar(), newM);
        return new Image(newM);
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

    public void drawLine(int x, int y, int height, Color c) {
        drawRectangle(x, y, x, y + height, c);
    }

    public void drawLine(int x, int height, Color c) {
        drawLine(x, 0, height, c);
    }

    public void drawLine(int x, Color c) {
        drawLine(x, 479, c);
    }

    /**
     * Draws a Rectangle on this Image.
     * @param r The Rectangle to draw.
     * @param c The Color of the Rectangle.
     */
    public void drawRectangle(Rectangle r, Color c) {
        drawRectangle(r.x, r.y, r.x + r.width, r.y + r.height, c);
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
        Imgproc.rectangle(m, new Point(x, y), new Point(x2, y2), c.toScalar());
    }

    /**
     * Draws Contours onto this Image.
     *
     * @param con The Contours to be drawn.
     * @param c   The Color of the Contours.
     */
    public void drawContours(List<Contour> con, Color c) {
        Imgproc.drawContours(m, Contour.toMatOfPoint(con), -1, c.toScalar());
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
        Imgproc.arrowedLine(m, new Point(x, y), new Point(x2, y2), c.toScalar());
    }

    /**
     * Draws text on this Image.
     *
     * @param text The text to be drawn.
     * @param x The X coordinate of the text's starting point.
     * @param y The Y coordinate of the text's starting point.
     * @param scale The scale of the text.
     * @param color The Color of the text.
     */
    public void drawText(String text, int x, int y, double scale, Color color) {
        //TODO: Find the right value for fontFace
        int fontFace = 0;

        Imgproc.putText(m, text, new Point(x, y), fontFace, scale, color.toScalar());
    }

    /**
     * Saves this Image as a file.
     *
     * @param fileName The name of the file.
     */
    public void saveAs(String fileName) {
        File f = new File(fileName);
        //f.mkdirs();
        if (f.exists()) f.delete();
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
