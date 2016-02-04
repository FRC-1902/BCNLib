package com.explodingbacon.bcnlib.networking;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 */

public class NetTable {
    NetworkTable table;

    /**
     * Default (server) constructor; used if this NetTable should be hosted on this machine.
     *
     * @param key A unique key to refer to this NetTable
     */
    public NetTable(String key) {
        table = NetworkTable.getTable(key);
    }

    /**
     * Client constructor; used if this NetTable is hosted on another machine.
     *
     * @param key A unique key to refer to this NetTable
     * @param ip  The IP address of the machine that this NetTable is hosted on.
     */
    public NetTable(String key, String ip) {
        NetworkTable.setClientMode();
        NetworkTable.setIPAddress(ip);
        table = NetworkTable.getTable(key);
    }

    public String getString(String val, String defaultVal) {
        try {
            return table.getString(val, defaultVal);
        } catch (ClassCastException e) {
            return defaultVal;
        }
    }

    public double getNumber(String val, double defaultVal) {
        try {
            return table.getNumber(val, defaultVal);
        } catch (ClassCastException e) {
            return defaultVal;
        }
    }

    public boolean getBoolean(String val, Boolean defaultVal) {
        try {
            return table.getBoolean(val, defaultVal);
        } catch (ClassCastException e) {
            return defaultVal;
        }
    }

    public void putString(String key, String val) {
        table.putString(key, val);
    }

    public void putNumber(String key, double val) {
        table.putNumber(key, val);
    }

    public void putBoolean(String key, boolean val) {
        table.putBoolean(key, val);
    }

    public String[] getAllKeys() {
        return null;
    }
}
