package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.actuators.Motor;
import com.explodingbacon.bcnlib.networking.NetTable;
import com.explodingbacon.bcnlib.networking.TableInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * A way to tune things using a NetTable
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */

public class NetTuner {
    private TableInterface table;
    private Map<String, Motor> motors = new HashMap<>();

    /**
     * The default, no-argument constructor
     */
    public NetTuner() {
        table = new NetTable("NetTuner");
    }

    /**
     * Add a tracked key to this NetTuner
     * @param key The key to add
     */
    public void addTrackedKey(String key) {
        table.putNumber(key, 0d);
    }

    /**
     * Get a value from a key in this NetTuner
     * @param key The key of the value to get.
     * @return
     */
    public double get(String key) {
        return table.getNumber(key, 0d);
    }

    /**
     * Set a Motor to tuning mode with the specified key
     * @param key They human-readable key used to name this Motor
     * @param motor The motor to be tuned
     */
    public void tune(String key, Motor motor) {
        addTrackedKey(key);
        motors.put(key, motor);
    }

    /**
     * Stop tuning a Motor with the given key
     * @param key The key of the Motor to stop tuning
     */
    public void stopTune(String key) {
        motors.remove(key);
    }

    /**
     * Refresh the table. Called by the framework.
     */
    public void refresh() {
        for (String key : motors.keySet()) {
            motors.get(key).setPower(this, get(key));
        }
    }
}
