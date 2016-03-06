package com.explodingbacon.bcnlib.pi4j;

import com.explodingbacon.bcnlib.actuators.SolenoidInterface;
import com.explodingbacon.bcnlib.framework.Log;
import com.pi4j.io.gpio.*;

/**
 * An implementation of SolenoidInterface that controls a Solenoid via a Raspberry Pi.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
 */

public class PiSolenoid implements SolenoidInterface { //High is off, low is on

    private GpioPinDigitalOutput gdo;
    private boolean on = false;

    /**
     * Creates a PiSolenoid.
     *
     * @param channel The channel the PiSolenoid is on.
     */
    public PiSolenoid(int channel) {
        if (!Pi.isInit()) Log.e("PiSolenoid made without the Pi being initialized!");
        gdo = Pi.getGPIOPin(channel);
        gdo.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public boolean get() {
        return on;
    }

    @Override
    public void set(boolean state) {
        on = state;
        if (on) {
            gdo.low();
        } else {
            gdo.high();
        }
    }
}
