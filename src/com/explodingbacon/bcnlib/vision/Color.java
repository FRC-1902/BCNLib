package com.explodingbacon.bcnlib.vision;

import org.opencv.core.Scalar;

/**
 * BCNLib implementation of Color, since the roboRIO does not have the java.awt package.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
 */

public class Color {

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color PURPLE = new Color(128, 0, 255);
    public static final Color ORANGE = new Color(255, 128, 0);
    public static final Color TEAL = new Color(0, 255, 255);
    public static final Color YELLOW = new Color(255, 255, 0);

    private int red = 0, green = 0, blue = 0;

    public Color(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    /**
    * Converts this Color to a Scalar (a class similar to Color that uses BGR instead of RGB)
    * @return A Scalar.
     */
    public Scalar toScalar() {
        return new Scalar(blue, green, red);
    }
}
