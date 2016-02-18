package com.explodingbacon.bcnlib.sensors;

/**
 * A class for standard Encoders.
 *
 * @author Ryan Shavell
 * @version 2016.2.17
 */

public class Encoder extends AbstractEncoder {

    private edu.wpi.first.wpilibj.Encoder enc;

    /**
     * Creates an Encoder.
     * @param aChannel This Encoder's first port.
     * @param bChannel This Encoder's second port.
     */
    public Encoder(int aChannel, int bChannel) {
        enc = new edu.wpi.first.wpilibj.Encoder(aChannel, bChannel);
    }

    /**
     * Creates an Encoder that is optionally reversed.
     * @param aChannel This Encoder's first port.
     * @param bChannel This Encoder's second port.
     * @param reverse If this Encoder should be reversed.
     */
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
