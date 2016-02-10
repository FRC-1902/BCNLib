package com.explodingbacon.bcnlib.pi4j;

import com.explodingbacon.bcnlib.framework.Log;
import edu.wpi.first.wpilibj.SpeedController;
import se.hirt.pi.adafruit.pwm.PWMDevice.PWMChannel;

public class PiMotor implements SpeedController {

    private PWMChannel channel;
    private double speed = 0;
    private double min = 0, center = 0, max = 0, loopTime = 0;
    public double deadbandMin = 0, deadbanMax = 0;

    public PiMotor(int port) {
        if (!Pi.isInit()) Log.e("PiMotor made without the Pi being initialized!");
        channel = Pi.getChannel(port);
    }

    @Override
    public double get() {
        return speed;
    }

    @Override
    public void set(double speed, byte syncGroup) {
        set(speed);
    }

    @Override
    public void set(double speed) {
        try {
            channel.setPWM(0, 255); //TODO: Convert speed to values that make sense for this function
            //Period = on + off
           /*
           * - 20ms periods (50 Hz) are the "safest" setting in that this works for all
           * devices - 20ms periods seem to be desirable for Vex Motors - 20ms periods
           * are the specified period for HS-322HD servos, but work reliably down to
           * 10.0 ms; starting at about 8.5ms, the servo sometimes hums and get hot; by
           * 5.0ms the hum is nearly continuous - 10ms periods work well for Victor 884
           * - 5ms periods allows higher update rates for Luminary Micro Jaguar speed
           * controllers. Due to the shipping firmware on the Jaguar, we can't run the
           * update period less than 5.05 ms.
           */

        } catch (Exception e) {}
    }

    /**
     * Converts a raw PWM value to a one-based value (a range of 1 to -1)
     * @param raw The raw PWM value.
     * @return A one-based value (a range of 1 to -1)
     */
    private double rawToOne(double raw) { //TODO: implement
        return 1;
    }

    /**
     * Converts a one-based value (a range of 1 to -1) to a raw PWM value.
     * @param one The one-based value (a range of 1 to -1)
     * @return A raw PWM value.
     */
    private double oneToRaw(double one) { //TODO: implement
        return 0;
    }

    public int doPWMValue(int val) {
        return 0;
    }

    public double getPositiveScaleFactor() {
        return max - min;
    }


    @Override
    public void setInverted(boolean isInverted) {} //Not implemented due to not being needed by BCNLib's Motor.java

    @Override
    public boolean getInverted() { return false; } //Not implemented due to not being needed by BCNLib's Motor.java

    @Override
    public void disable() {} //Not implemented due to not being needed by BCNLib's Motor.java

    @Override
    public void pidWrite(double output) {} //Not implemented due to not being needed by BCNLib's Motor.java
}
