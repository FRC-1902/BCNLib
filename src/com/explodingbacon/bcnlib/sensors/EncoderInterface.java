package com.explodingbacon.bcnlib.sensors;

/**
 * An interface for Encoders.
 *
 * @author Ryan Shavell
 * @version 2016.2.6
 */

public interface EncoderInterface {
    public byte RATE = 1;
    public byte POSITION = 2;

    public void reset();

    public double getRate();

    public int get();

}
