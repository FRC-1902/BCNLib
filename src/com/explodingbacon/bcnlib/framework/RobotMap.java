package com.explodingbacon.bcnlib.framework;

import java.util.HashMap;
import java.util.Map;

//TODO: Either rework to be useful or delete this?

/**
 * A class to manage what ports each actuator and sensor are in.
 * <br><br>
 * USAGE: Extend this class with a constructor that repeatedly calls <code>self.register</code> with a String name and
 * Integer motor port.
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public class RobotMap {
    private Map<String, Integer> m =  new HashMap<>();

    /**
     * Register a device/port combo.
     * @param id The ID of the device you'd like to refer to it by
     * @param port The port that the device is in
     */
    private void register(String id, Integer port) {
        m.put(id, port);
    }

    /**
     * Retrieve the port of a device
     * @param id The ID of the device that you'd like to refer to
     * @return The port that the device is in
     */
    public Integer get(String id) {
        return m.get(id);
    }
}
