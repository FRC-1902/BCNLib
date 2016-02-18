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
    Thread t;

    /**
     * Creates a PDP.
     */
    public PDP() {
        this.pdp = new PowerDistributionPanel();
        t = new Thread(breakerRunnable);
        t.run();
    }

    /**
     * Sets code to be run in the event that a certain breaker is tripped.
     * @param port The breaker.
     * @param r The code to be run.
     */
    public void setOnBreakerTrip(int port, Runnable r) {
        portRunnables.put(port, r);
    }

    private Runnable breakerRunnable = () -> {
        for(int i = 0; i < 5; i++) {
            if(pdp.getCurrent(i) >= 40) {
                Log.d("Oh no! We might have tripped a breaker on port " + i + " with a current of " + pdp.getCurrent(i));
                Runnable r = portRunnables.get(i);
                if (r != null) r.run();
            }
        }

        for (int i = 5; i < 12; i++) {
            if(pdp.getCurrent(i) >= 30) {
                Log.d("Oh no! We might have tripped a breaker on port " + i + " with a current of " + pdp.getCurrent(i));
                Runnable r = portRunnables.get(i);
                if (r != null) r.run();
            }
        }

        for (int i = 12; i < 15; i++) {
            if(pdp.getCurrent(i) >= 30) {
                Log.d("Oh no! We might have tripped a breaker on port " + i + " with a current of " + pdp.getCurrent(i));
                Runnable r = portRunnables.get(i);
                if (r != null) r.run();
            }
        }
    };

    /**
     * Gets the current on the current port.
     * @param port The port to check.
     * @return The current on the current port.
     */
    public double getCurrent(int port) {
        return pdp.getCurrent(port);
    }

    /**
     * Gets the WPILib object for the PDP.
     * @return The WPILib object for the PDP.
     */
    public PowerDistributionPanel getPDP() {
        return pdp;
    }
}
