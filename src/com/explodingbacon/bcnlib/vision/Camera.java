package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

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
            cam.set(Videoio.CV_CAP_PROP_BUFFERSIZE, 1);
            Thread.sleep(1000);
            if (!isOpen()) {
                Log.e("Camera error!");
            }
        } catch (Exception e) {
            Log.e("Camera exception!");
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
        Mat m = new Mat();
        grab();
        cam.retrieve(m); //Was read
        return new Image(m);
    }

    public void grab() {
        cam.grab();
    }

    /**
     * Releases this Camera.
     */
    public void release() {
        cam.release();
    }
}