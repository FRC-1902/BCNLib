package com.explodingbacon.bcnlib.vision;

import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * BCNLib implementation of Color, since the roboRIO does not have the java.awt package.
 *
 * @author Ryan Shavell
 * @version 2016.3.21
 */

public class Color extends BCNScalar {

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color PURPLE = new Color(128, 0, 255);
    public static final Color ORANGE = new Color(255, 128, 0);
    public static final Color TEAL = new Color(0, 255, 255);
    public static final Color YELLOW = new Color(255, 255, 0);

    private int red, green, blue;

    /**
     * Creates a Color from RGB values.
     *
     * @param r The Red value.
     * @param g The Green value.
     * @param b The Blue value.
     */
    public Color(int r, int g, int b) {
        super(b, g, r); //OpenCV works in BGR, not RGB
        red = r;
        green = g;
        blue = b;
    }

    /**
     * Gets the Red value of this Color.
     *
     * @return The Red value of this Color.
     */
    public int getRed() {
        return red;
    }

    /**
     * Gets the Green value of this Color.
     *
     * @return The Green value of this Color.
     */
    public int getGreen() {
        return green;
    }

    /**
     * Gets the Blue value of this Color.
     * @return The Blue value of this Color.
     */
    public int getBlue() {
        return blue;
    }
}
