package com.explodingbacon.bcnlib.vision;

import org.opencv.core.Scalar;

/**
 * BCNLib implementation of Color, since the roboRIO does not have the java.awt package.
 *
 * @author Ryan Shavell
 * @version 2016.3.8
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

    private int red, green, blue;

    /**
     * Creates a Color from RGB values.
     *
     * @param r The Red value.
     * @param g The Green value.
     * @param b The Blue value.
     */
    public Color(int r, int g, int b) {
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

    /**
    * Converts this Color to a Scalar (a class similar to Color that uses BGR instead of RGB)
     *
    * @return A Scalar.
     */
    public Scalar toScalar() {
        return new Scalar(blue, green, red);
    }
}
