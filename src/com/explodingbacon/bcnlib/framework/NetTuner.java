package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.actuators.Motor;

import java.util.HashMap;
import java.util.Map;

public class NetTuner {
    private NetTable table;
    private Map<String, Motor> motors = new HashMap<>();

    protected NetTuner() {
        table = new NetTable("NetTuner");
    }

    public void addTrackedKey(String key) {
        key = key.replace(' ', '_');
        table.putNumber(key, 0);
    }

    public double get(String key) {
        key = key.replace(' ', '_');
        return table.getNumber(key, 0);
    }

    public void tune(String key, Motor motor) {
        key = key.replace(' ', '_');
        addTrackedKey(key);
        motors.put(key, motor);
    }

    protected void refresh() {
        for (String key : motors.keySet()) {
            motors.get(key).setPower(this, get(key));
        }
    }
}
