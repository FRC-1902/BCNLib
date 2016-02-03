package com.explodingbacon.bcnlib.framework;

public class Encoder implements EncoderInterface {

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
