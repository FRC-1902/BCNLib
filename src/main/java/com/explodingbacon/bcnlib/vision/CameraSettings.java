package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Class that uses v4l2-utils to control camera settings.
 *
 * @author Ryan Shavell
 * @version 2017.1.14
 */

public class CameraSettings {

    /**
     * Sets the exposure level of the USB Camera.
     * @param exposure The wanted exposure level.
     */
    public static void setExposure(int exposure) {
        runCommand("v4l2-ctl -c exposure_absolute=" + exposure);
        Log.i("Camera Exposure set to " + exposure + ".");
    }

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
