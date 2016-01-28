package com.explodingbacon.bcnlib.vision;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Image {

    private Mat m;

    protected Image() {
        m = new Mat();
    }

    /**
     * Gets the width of this Image.
     * @return The width of this Image.
     */
    public int getWidth() {
        return m.width();
    }

    /**
     * Gets the height of this Image.
     * @return The height of this Image.
     */
    public int getHeight() {
        return m.height();
    }

    /**
     * Gets the outlines of this Image.
     * @return The outlines of this Image.
     */
    public List<MatOfPoint> getOutlines() {
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(m, contours, new Mat(), Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
        return contours;
    }

    /**
     * Sets the color range of this Image. Anything outside this range becomes black.
     * @param low The lowest acceptable color.
     * @param high The highest acceptable color.
     */
    public void setColorRange(Color low, Color high) {
        m.colRange(VisUtil.toBGR(low), VisUtil.toBGR(high));
    }

    /**
     * Creates an identical copy of this Image.
     * @return An identical copy of this Image.
     */
    public Image copy() {
        Image cop = new Image();
        Mat copM = new Mat();
        m.copyTo(copM);
        cop.setMat(copM);
        return cop;
    }

    /**
     * Draws a Rectangle on this Image.
     * @param x The X coordinate of the Rectangle.
     * @param y The Y coordinate of the Rectangle.
     * @param x2 The second X coordinate of the Rectangle.
     * @param y2 The second Y coordinate of the Rectangle.
     * @param c The Color of the Rectangle.
     */
    public void drawRectangle(int x, int y, int x2, int y2, Color c) {
        Imgproc.rectangle(m, new Point(x, y), new Point(x2, y2), VisUtil.toScalar(c));
    }

    /**
     * Draws previously obtained outlines onto this Image.
     * @param contours The outlines to be drawn (gotten from Image.getOutlines())
     * @param c The Color of the outlines.
     */
    public void drawOutlines(List<MatOfPoint> contours, Color c) {
        Imgproc.drawContours(m, contours, -1, VisUtil.toScalar(c));
    }

    /**
     * Saves this Image as a file.
     * @param fileName The name of the file.
     */
    public void saveAs(String fileName) {
        Imgcodecs.imwrite(fileName, m);
    }

    protected Mat getMat() {
        return m;
    }

    protected void setMat(Mat newMat) {
        m = newMat;
    }
}
