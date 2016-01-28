package com.explodingbacon.bcnlib.vision;

import org.opencv.videoio.VideoCapture;

public class Camera {

    private VideoCapture cam;

    public Camera(int index) {
        try {
            cam = new VideoCapture(index);
            Thread.sleep(1000);
            if (!isOpen()) {
                System.out.println("Camera error!");
            } else {
                System.out.println("Camera working!");
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
     * Gets the current frame of the Camera.
     * @return The current frame of the Camera.
     */
    public Image getFrame() {
        Image i = new Image();
        cam.read(i.getMat());
        return i;
    }

    /**
     * Releases this Camera.
     */
    public void release() {
        cam.release();
    }
}