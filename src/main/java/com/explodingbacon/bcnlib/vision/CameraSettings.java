package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Class that uses v4l2-utils to control camera settings.
 *
 * @author Ryan Shavell
 * @version 2017.1.17
 */

public class CameraSettings {

    /**
     * Sets the USB Camera's auto-adjust exposure settings.
     *
     * @param value
     */
    public static void setExposureAuto(int value) {
        runCommand("v4l2-ctl -c exposure_auto=" + value);
    }

    /**
     * Sets the exposure level of the USB Camera.
     * @param exposure The wanted exposure level.
     */
    public static void setExposure(int exposure) {
        runCommand("v4l2-ctl -c exposure_absolute=" + exposure);
        Log.i("Camera Exposure set to " + exposure + ".");
    }

    //List of "real" exposure values
    /*
    9
    19/20
    39    <- stopped here

    TODO: Complete this list and/or check if the one on delphi that works for the Lifecam 3000 works for this camera as well

     */

    private static String runCommand(String command) {
        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}
