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
 * @version 2016.3.2
 */

public class Camera {

    private VideoCapture cam;
    private int index = 0;
    private final Object CAMERA_USE = new Object();

    private boolean autoUpdate;
    private Thread updateThread = null;

    public Camera(int i, boolean b) {
        index = i;
        try {
            cam = new VideoCapture(index);
            autoUpdate = b;
            Thread.sleep(500);
            if (autoUpdate) {
                updateThread = new Thread(() -> {
                    //Log.d("Camera autoupdate thread init");
                    while (true) {
                        synchronized (CAMERA_USE) {
                            if (cam.isOpened()) {
                                if (!cam.grab()) {
                                    Log.d("Camera.grab() returned false!");
                                }
                            }
                        }
                    }
                });
                updateThread.start();
            } else {
                cam.release();
            }
        } catch (Exception e) {
            Log.e("Camera init exception!");
            e.printStackTrace();
        }
    }

    /**
     * Checks if this Camera is open.
     *
     * @return If this Camera is open.
     */
    public boolean isOpen() {
        return cam.isOpened();
    }

    /**
     * Checks if this Camera is auto updating its frames.
     *
     * @return If this Camera is auto updating its frames.
     */
    public boolean isAutoUpdating() {
        return autoUpdate;
    }

    /**
     * Gets the current image of the Camera.
     *
     * @return The current image of the Camera.
     */
    public Image getImage() {
        synchronized (CAMERA_USE) {
            if (autoUpdate) {
                Mat m = new Mat();
                cam.retrieve(m);
                return new Image(m);
            } else {
                Mat m = new Mat();
                cam.open(index);
                cam.grab();
                cam.retrieve(m);
                cam.release();
                return new Image(m);
            }
        }
    }

    /**
     * Opens this Camera.
     */
    public void open() {
        cam.open(index);
    }

    /**
     * Releases this Camera.
     */
    public void release() {
        cam.release();
    }

    /**
     * Gets the value of an OpenCV property.
     *
     * @param propid The property ID. Should be a variable defined in Videoio.java.
     * @return The value of an OpenCV property.
     */
    public double getRaw(int propid) {
        return cam.get(propid);
    }

    /**
     * Sets the value of an OpenCV property.
     *
     * @param propid The property ID. Should be a variable defined in Videoio.java.
     * @param val The value to set the property to.
     * @return If changing the property was successful.
     */
    public boolean setRaw(int propid, double val) {
        return cam.set(propid, val);
    }
}