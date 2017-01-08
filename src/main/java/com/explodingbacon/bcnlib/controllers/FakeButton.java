package com.explodingbacon.bcnlib.controllers;

import com.explodingbacon.bcnlib.framework.Log;
import java.util.function.BooleanSupplier;

/**
 * A class for a programmedly controllable Button.
 *
 * @author Ryan Shavell
 * @version 2016.3.5
 */

public class FakeButton implements Button {

    private boolean status = false;
    private BooleanSupplier supplier = null;

    /**
     * Creates a FakeButton.
     */
    public FakeButton() {}

    /**
     * Creates a FakeButton whose state is controlled by the result of the BooleanSupplier.
     *
     * @param s The BooleanSupplier.
     */
    public FakeButton(BooleanSupplier s) {
        supplier = s;
    }

    @Override
    public boolean get() {
        if (supplier != null) {
            return supplier.getAsBoolean();
        } else {
            return status;
        }
    }

    /**
     * Sets the status of this Button.
     *
     * @param b The status of this Button.
     */
    public void set(boolean b) {
        if (supplier == null) {
            status = b;
        } else {
            Log.e("Called FakeButton.set() on a FakeButton that uses a BooleanSupplier to track it's status!");
        }
    }
}
