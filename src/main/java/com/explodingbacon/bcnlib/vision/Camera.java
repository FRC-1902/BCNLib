package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.framework.Log;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import java.util.function.Consumer;

/**
 * A wrapper class for OpenCV's VideoCapture object.
 *
 * @author Ryan Shavell
 * @version 2016.6.16
 */

public class Camera {

    private VideoCapture cam;
    private int index;
    private final Object CAMERA_USE = new Object();
    private final Object IMAGE_USE = new Object();
    private Image image = new Image();
    private Consumer<Image> onEachFrame = null;

    private boolean autoUpdate;
    private boolean updatingEnabled = true;
    private Thread updateThread = null;

    /**
     * Creates a new Camera.
     *
     * @param i The ID of the Camera, 0-indexed. If there is only one Camera available, it should be ID 0.
     * @param b If this Camera should keep the frames served by Camera.getImage() up to date.
     */
    public Camera(int i, boolean b) {
        index = i;
        try {
            cam = new VideoCapture(index);
            autoUpdate = b;
            Thread.sleep(1000);
            if (autoUpdate) {
                updateThread = new Thread(() -> {
                    while (true) {
                        if (cam.isOpened() && updatingEnabled) {
                            synchronized (CAMERA_USE) {
                                if (cam.isOpened()) { //Do this again because synchronized can cause delays
                                    synchronized (IMAGE_USE) {
                                        cam.read(image.getMat());
                                        try {
                                            if (onEachFrame != null) {
                                                Image copy = image.copy();
                                                onEachFrame.accept(copy);
                                            }
                                        } catch (Exception e) {
                                            Log.e("Camera.onEachFrame Runnable error!");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                updateThread.start();
            }
        } catch (Exception e) {
            Log.e("Camera init exception!");
            e.printStackTrace();
        }
    }

    /**
     * Checks if the auto updating for this Camera is enabled. This is NOT used to check if the Camera is in auto
     * updating mode. Use isAutoUpdating() for that.
     *
     * @return If the auto updating for this Camera is enabled.
     */
    public boolean isUpdatingEnabled() {
        return updatingEnabled;
    }

    /**
     * Sets if the auto updating for this Camera is enabled.
     *
     * @param u The new status of the auto updating.
     */
    public void setUpdatingEnabled(boolean u) {
        updatingEnabled = u;
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
     * Sets the code that runs every time a frame is processed. Set this to null if you want no code to run.
     *
     * @param c The code to be run.
     */
    public void onEachFrame(Consumer<Image> c) {
        onEachFrame = c;
    }

    /**
     * Gets the current Image on this Camera.
     *
     * @return The current Image on this Camera.
     */
    public Image getImage() {
        if (!autoUpdate) {
            synchronized (CAMERA_USE) {
                cam.release();
                cam.open(index);
                cam.read(image.getMat());
                cam.release();
            }
        }
        synchronized (IMAGE_USE) {
            return image.copy();
        }
    }

    /**
     * Opens this Camera.
     */
    public void open() {
        synchronized (CAMERA_USE) {
            cam.open(index);
        }
    }

    /**
     * Releases this Camera.
     */
    public void release() {
        synchronized (CAMERA_USE) {
            cam.release();
        }
    }

    /**
     * Gets the FPS of this Camera.
     *
     * @return The FPS of this Camera.
     */
    public double getFPS() {
        return getRaw(Videoio.CAP_PROP_FPS);
    }

    /**
     * Sets the FPS of this Camera. TODO: Figure out why this doesn't work. This should help: http://stackoverflow.com/questions/7039575/how-to-set-camera-fps-in-opencv-cv-cap-prop-fps-is-a-fake
     *
     * @param d The new FPS.
     * @return If the operation was successful.
     */
    public boolean setFPS(double d) {
        return setRaw(Videoio.CAP_PROP_FPS, d);
    }

    /**
     * Gets the exposure of this Camera.
     *
     * @return The exposure of this Camera.
     */
    public double getExposure() {
        return getRaw(Videoio.CAP_PROP_EXPOSURE);
    }

    /**
     * Sets the exposure of this Camera.
     *
     * @param d The new exposure.
     * @return If the operation was successful.
     */
    public boolean setExposure(double d) {
        return setRaw(Videoio.CAP_PROP_EXPOSURE, d);
    }

    /**
     * Gets the value of an OpenCV property.
     *
     * @param propid The property ID. Should be a variable defined in Videoio.
     * @return The value of an OpenCV property.
     */
    public double getRaw(int propid) {
        return cam.get(propid);
    }

    /**
     * Sets the value of an OpenCV property.
     *
     * @param propid The property ID. Should be a variable defined in Videoio.
     * @param val The value to set the property to.
     * @return If changing the property was successful.
     */
    public boolean setRaw(int propid, double val) {
        synchronized (CAMERA_USE) {
            boolean result = cam.set(propid, val);
            if (!result) Log.w("Setting a property of Camera " + index + " failed!");
            return result;
        }
    }
}