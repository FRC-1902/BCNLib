package com.explodingbacon.bcnlib.vision;

import org.opencv.core.Scalar;

public class Color {

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
