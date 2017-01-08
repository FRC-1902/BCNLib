package com.explodingbacon.bcnlib.vision;

/**
 * An Object that represents an HSV (Hue, Saturation, Value) value.
 *
 * @author Ryan Shavell
 * @version 2016.6.16
 */
public class HSV extends BCNScalar {

    private int h, s, v;

    /**
     * Creates an HSV value set.
     *
     * @param h The Hue value.
     * @param s The Saturation value.
     * @param v The Value value.
     */
    public HSV(int h, int s, int v) {
        super(h, s, v);
        this.h = h;
        this.s = s;
        this.v = v;
    }

    /**
     * Gets the Hue value.
     *
     * @return The Hue value.
     */
    public int getHue() {
        return h;
    }

    /**
     * Gets the Saturation value.
     *
     * @return The Saturation value.
     */
    public int getSaturation() {
        return s;
    }

    /**
     * Gets the Value value.
     *
     * @return The Value value.
     */
    public int getValue() {
        return v;
    }
}
