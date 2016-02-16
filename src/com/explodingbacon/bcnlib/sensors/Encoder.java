package com.explodingbacon.bcnlib.sensors;

/**
 * A class for standard Encoders.
 *
 * @author Ryan Shavell
 * @version 2016.2.15
 */

public class Encoder extends AbstractEncoder {

    private edu.wpi.first.wpilibj.Encoder enc;

    public Encoder(int aChannel, int bChannel) {
        enc = new edu.wpi.first.wpilibj.Encoder(aChannel, bChannel);
    }

    public Encoder(int aChannel, int bChannel, boolean reverse) {
        enc = new edu.wpi.first.wpilibj.Encoder(aChannel, bChannel, reverse);
    }

    @Override
    public void reset() {
        enc.reset();
    }

    @Override
    public double getRate() {
        return enc.getRate();
    }

    @Override
    public int get() {
        return enc.getRaw();
    }
}
