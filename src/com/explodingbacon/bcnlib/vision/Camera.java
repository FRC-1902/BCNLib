package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;
import com.explodingbacon.bcnlib.utils.CodeThread;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

/**
 * A wrapper class for OpenCV's VideoCapture object.
 *
 * @author Ryan Shavell
 * @version 2016.2.22
 */

public class Camera {

    private VideoCapture cam;
    private int index = 0;
    private static Object CAMERA_USE = new Object();

    public Camera(int i) {
        index = i;
        try {
            cam = new VideoCapture(index);
            Thread.sleep(500);
            cam.release();
        } catch (Exception e) {
            Log.e("Camera init exception!");
            e.printStackTrace();
        }
    }

    /**
     * Checks if this Camera is open.
     * @return If this Camera is open.
     */
    public boolean isOpen() {
        return cam.isOpened();
    }

    /**
     * Gets the current image of the Camera.
     * @return The current image of the Camera.
     */
    public Image getImage() {
        synchronized (CAMERA_USE) {
            Mat m = new Mat();
            cam.open(index);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
            cam.grab();
            cam.retrieve(m);
            cam.release();
            return new Image(m);
        }
    }

    /**
     * Releases this Camera.
     */
    public void release() {
        cam.release();
    }

    /**
     * Gets the value of an OpenCV property.
     * @param propid The property ID. Should be a variable defined in Videoio.java.
     * @return The value of an OpenCV property.
     */
    public double getRaw(int propid) {
        return cam.get(propid);
    }

    /**
     * Sets the value of an OpenCV property.
     * @param propid The property ID. Should be a variable defined in Videoio.java.
     * @param val The value to set the property to.
     * @return If changing the property was successful.
     */
    public boolean setRaw(int propid, double val) {
        return cam.set(propid, val);
    }
}