package com.explodingbacon.bcnlib.sensors;

/**
 * An interface for Encoders.
 *
 * @author Ryan Shavell
 * @version 2016.2.8
 */

public interface EncoderInterface {
    byte RATE = 1;
    byte POSITION = 2;

    void reset();

    double getRate();

    int get();

}
