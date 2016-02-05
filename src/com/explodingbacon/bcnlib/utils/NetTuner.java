package com.explodingbacon.bcnlib.utils;

import com.explodingbacon.bcnlib.actuators.Motor;
import com.explodingbacon.bcnlib.networking.NetTable;
import com.explodingbacon.bcnlib.networking.TableInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 */

public class NetTuner {
    private TableInterface table;
    private Map<String, Motor> motors = new HashMap<>();

    public NetTuner() {
        table = new NetTable("NetTuner");
    }

    public void addTrackedKey(String key) {
        table.putNumber(key, 0d);
    }

    public double get(String key) {
        return table.getNumber(key, 0d);
    }

    public void tune(String key, Motor motor) {
        addTrackedKey(key);
        motors.put(key, motor);
    }

    public void stopTune(String key) {
        motors.remove(key);
    }

    public void refresh() {
        for (String key : motors.keySet()) {
            motors.get(key).setPower(this, get(key));
        }
    }
}
