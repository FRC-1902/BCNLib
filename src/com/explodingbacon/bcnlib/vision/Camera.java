package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 * A wrapper class for OpenCV's VideoCapture object.
 *
 * @author Ryan Shavell
 * @version 2016.2.15
 */

public class Camera {

    private VideoCapture cam;

    public Camera(int index) {
        try {
            cam = new VideoCapture(index);
            Thread.sleep(1000);
            if (!isOpen()) {
                System.out.println("Camera error!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        Mat m = new Mat();
        cam.read(m);
        return new Image(m);
    }

    /**
     * Releases this Camera.
     */
    public void release() {
        cam.release();
    }
}