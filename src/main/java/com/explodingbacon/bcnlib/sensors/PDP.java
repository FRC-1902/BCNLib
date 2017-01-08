package com.explodingbacon.bcnlib.sensors;

import com.explodingbacon.bcnlib.framework.Log;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import java.util.HashMap;

/**
 * A class for the Power Distribution Panel.
 *
 * @author Dominic Canora
 * @version 2016.1.0
 */

public class PDP {
    private PowerDistributionPanel pdp;
    private HashMap<Integer, Runnable> portRunnables = new HashMap<>();
    private boolean loggingTripping = true;
    //private PrintWriter logger;
    Thread t;

    /**
     * Creates a PDP.
     */
    public PDP() {
        this.pdp = new PowerDistributionPanel();

        t = new Thread(breakerRunnable);
        t.start();
    }

    /**
     * Checks if the PDP will create log messages if a breaker might be tripping.
     *
     * @return If the PDP will create log messages if a breaker might be tripping.
     */
    public boolean isLoggingTripping() {
        return loggingTripping;
    }

    /**
     * Sets if the PDP should create log messages if a breaker might be tripping.
     *
     * @param b If the PDP should create log messages if a breaker might be tripping.
     */
    public void setLoggingTripping(boolean b) {
        loggingTripping = b;
    }

    /**
     * Sets code to be run in the event that a certain breaker is tripped.
     *
     * @param port The breaker.
     * @param r The code to be run.
     */
    public void setOnBreakerTrip(int port, Runnable r) {
        portRunnables.put(port, r);
    }

    private Runnable breakerRunnable = () -> {
        boolean warnedAboutWriter = false;
        while(true) {
            if (loggingTripping) {
                for (int i = 0; i < 16; i++) {
                    int maxCurrent = getMaxCurrent(i);
                    double current = pdp.getCurrent(i);
                    if (current >= maxCurrent) {
                        Log.d("We might have tripped a " + maxCurrent + " amp breaker on port " + i + " with a current of " + current + "!");
                        Runnable r = portRunnables.get(i);
                        if (r != null) r.run();
                    }
                }
            }

            String s = "";
            for (int i = 0; i <= 15; i++) {
                s = s.concat(getCurrent(i) + (i == 15 ? "\n" : ","));
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    };

    /**
     * Gets the max current for a specific port on this PDP.
     *
     * @param port The port.
     * @return The max current for a specific port on this PDP.
     */
    public int getMaxCurrent(int port) {
        if (port > -1 && port < 4) {
            return 40;
        } else if (port > 11 && port < 16) {
            return 40;
        } else if (port > 3 && port < 12) {
            return 30;
        } else {
            return -1;
        }
    }

    /**
     * Gets the current on the current port.
     *
     * @param port The port to check.
     * @return The current on the current port.
     */
    public double getCurrent(int port) {
        return pdp.getCurrent(port);
    }


    /**
     * Gets the WPILib object for the PDP.
     *
     * @return The WPILib object for the PDP.
     */
    public PowerDistributionPanel getPDP() {
        return pdp;
    }
}
