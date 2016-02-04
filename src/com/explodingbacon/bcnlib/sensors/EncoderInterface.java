package com.explodingbacon.bcnlib.sensors;

public interface EncoderInterface {
    public byte RATE = 1;
    public byte POSITION = 2;

    public void reset();

    public double getRate();

    public int get();

}
