package com.explodingbacon.bcnlib.actuators;

import com.explodingbacon.bcnlib.framework.Command;
import com.explodingbacon.bcnlib.framework.Log;

/**
 * Usables are objects that can be claimed by Commands, making them (if programmed properly), claimable by a single Command.
 * When claimed by a Command, the Usable (again, if programmed properly) will not be usable by any other Commands until
 * the first Command releases the Usable.
 *
 * @author Ryan Shavell
 * @version 2016.3.10
 */

public abstract class Usable {

    private Class user = null;
    private Runnable onNoUser = null;

    /**
     * Gets the current user of this Usable.
     *
     * @return The current user of this Usable.
     */
    public Class getUser() {
        return user;
    }

    /**
     * Sets the current Command that is using this Usable.
     *
     * @param c The Command that is using this Usable, or null if no command is using this Usable.
     */
    public void setUser(Command c) {
        if (c != null && !isUsableBy(c)) {
            Log.w("\"" + c.getClass().getSimpleName() + "\" took control of a Usable that was being used by \"" + user.getSimpleName() + "\"!");
        }
        boolean userWasNull = user == null;
        user = (c == null ? null : c.getClass());
        if (user == null && !userWasNull && onNoUser != null) {
            try {
                onNoUser.run();
            } catch (Exception e) {
                Log.e("Usable.onNoUser exception!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the code that will run if this Usable has no user.
     */
    public void onNoUser(Runnable r) {
        onNoUser = r;
    }

    /**
     * Checks if this Usable is currently being used by a Command.
     *
     * @return If this Usable is currently being used by a Command.
     */
    public boolean isBeingUsed() {
        return user != null;
    }

    /**
     * Checks if this Usable is usable by a Command. This Usable is usable by the Command if the Command is already using
     * this Usable or if there is no user.
     *
     * @param c The Command.
     * @return If this Usable is usable by the Command.
     */
    public Boolean isUsableBy(Command c) {
        return (user == c.getClass() || !isBeingUsed());
    }

    /**
     * Has a Command attempt to claim this Usable. This Usable can be claimed by the Command if the Command is already this Usable's
     * user or if this Usable has no user.
     *
     * @param c The Command.
     * @return True if the claim was successful, false if it was not. Generally, do not actuate this Usable if this returns false.
     */
    public boolean claim(Command c) {
        if (isUsableBy(c)) {
            setUser(c);
            return true;
        } else {
            return false;
        }
    }
}
