package com.explodingbacon.bcnlib.networking;

import com.explodingbacon.bcnlib.controllers.Button;
import com.explodingbacon.bcnlib.framework.AbstractOI;
import edu.wpi.first.wpilibj.buttons.InternalButton;

/**
 * @author Dominic Canora
 * @version 2016.1.0
 */

public class NetButton implements Button {
    private InternalButton button;
    private boolean last = false;
    private String key;

    /**
     * Default Constructor
     *
     * @param key A unique key to refer to this NetButton
     */
    public NetButton(String key) {
        button = new InternalButton();
        this.key = key;
        AbstractOI.addNetButton(this);
    }

    /**
     * Refresh the value of this NetButton. Automatically handled by AbstractOI.
     */
    public void refresh() {
        button.setPressed(AbstractOI.table.getBoolean(key, false));
    }

    /**
     * Get the value of this NetButton
     *
     * @return The value of this NetButton
     */
    public boolean get() {
        return last = button.get();
    }

}
