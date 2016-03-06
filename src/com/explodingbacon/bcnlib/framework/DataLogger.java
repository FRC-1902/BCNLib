package com.explodingbacon.bcnlib.framework;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.function.Supplier;

/**
 * A class for logging data and saving to do a .csv file.
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */
public class DataLogger {
    private PrintWriter writer;
    private Thread t;
    private final LinkedHashMap<String, Supplier> map = new LinkedHashMap<>();
    private boolean started = false;

    public DataLogger() {
        try {
            writer = new PrintWriter("/var/log/BCNLib/" + System.currentTimeMillis() + ".csv");
        } catch (Exception e) {
            Log.e("Exception in DataLogger!");
            e.printStackTrace();
        }

        map.put("Timestamp", System::currentTimeMillis);

        t = new Thread(refreshRunnable);
    }

    public void log(String key, Supplier supplier) {
        if(started) {
            Log.e("DataLogger ignored a request to add a tracked value because it has already started logging.");
            return;
        }

        map.put(key, supplier);
    }

    public void startLogging() {
        started = true;
        t.run();

        String s = "";

        for (String key : map.keySet()) {
            s = s.concat(key);
        }

        s = s.substring(0, s.length() - 1).concat("\n");

        writer.write(s);
    }

    private Runnable refreshRunnable = () -> {
        String s = "";
        for(String key : map.keySet()) {
            s = s + map.get(key).get();
        }

        s = s.substring(0, s.length() - 1).concat("\n");

        writer.write(s);
    };
}
