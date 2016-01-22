package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.actuators.MotorInterface;

public class NetTuner {
    private NetTable table;

    public NetTuner() {
        table = new NetTable("NetTuner");
    }

    public double get(String key) {
        return table.getNumber(key, 0);
    }

    public void tune(MotorInterface motor) {

    }
}
