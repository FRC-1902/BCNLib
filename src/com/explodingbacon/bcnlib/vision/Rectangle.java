package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.utils.Utils;
import org.opencv.core.Rect;

/**
 * BCNLib implementation of Rectangle, since the roboRIO does not have the java.awt package.
 *
 * @author Ryan Shavell
 * @version 2016.3.7
 */

public class Rectangle {

    public final int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(double x, double y, double width, double height) {
        this.x = Utils.round(x);
        this.y = Utils.round(y);
        this.width = Utils.round(width);
        this.height = Utils.round(height);
    }

    /**
     * Creates an OpenCV Rect the same dimensions as this Rectangle.
     * @return An OpenCV Rect the same dimensions as this Rectangle.
     */
    public Rect toRect() {
        return new Rect(x, y, width, height);
    }
}
