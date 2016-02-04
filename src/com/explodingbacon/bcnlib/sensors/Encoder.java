package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.PIDSource;

public class Encoder implements EncoderInterface, PIDSource {

    private edu.wpi.first.wpilibj.Encoder enc;
    private byte pidMode;

    public Encoder(int aChannel, int bChannel) {
        enc = new edu.wpi.first.wpilibj.Encoder(aChannel, bChannel);
    }

    public Encoder(int aChannel, int bChannel, boolean reverse) {
        enc = new edu.wpi.first.wpilibj.Encoder(aChannel, bChannel, reverse);
    }

    public void setPidMode(byte mode) {
        this.pidMode = mode;
    }

    @Override
    public double getForPID() {
        if (pidMode == EncoderInterface.POSITION) return get();
        if (pidMode == EncoderInterface.RATE) return getRate();
        return 0;
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
