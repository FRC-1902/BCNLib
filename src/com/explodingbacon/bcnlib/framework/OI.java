package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.utils.CodeThread;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles spawning commands based off of button inputs.
 * TODO(?): Read from NetworkTables for button inputs (Are we still doing that?)
 *
 * @author Ryan Shavell
 * @version 2016.1.7
 */

public class OI extends CodeThread {

    private List<Trigger> triggers = new ArrayList<>();
    private boolean started = false;

    /**
     * Makes a command run when a button is pressed.
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whenPressed(Button b, Command c) {
        if (!isRunning()) {
            triggers.add(new Trigger(c, b, TriggerType.PRESS));
        } else {
            System.out.println("[ERROR] Commands are being added to OI while it is running!");
        }
    }

    /**
     * Makes a command run when a button is released.
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whenReleased(Button b, Command c) {
        if (!isRunning()) {
            triggers.add(new Trigger(c, b, TriggerType.RELEASE));
        } else {
            System.out.println("[ERROR] Commands are being added to OI while it is running!");
        }
    }

    /**
     * Makes a command run while a button is held. If the command terminates and the button is still held, the command will start again.
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whileHeld(Button b, Command c) {
        if (!isRunning()) {
            triggers.add(new Trigger(c, b, TriggerType.WHILE_HELD));
        } else {
            System.out.println("[ERROR] Commands are being added to OI while it is running!");
        }
    }

    @Override
    public void code() {
        for (Trigger t : triggers) {
            if (t.t == TriggerType.PRESS) {
                if (!t.b.getPrevious() && t.b.get()) { //If the button wasn't pressed before and now is
                    t.c.start();
                }
            } else if (t.t == TriggerType.RELEASE) {
                if (t.b.getPrevious() && !t.b.get()) { //If the button was pressed before and now isn't
                    t.c.start();
                }
            } else if (t.t == TriggerType.WHILE_HELD) {
                if (t.b.get() && !t.c.isRunning()) { //If the button is pressed and the command isn't started already
                    t.c.start();
                } else if (!t.b.get() && t.c.isRunning()) { //If the button is released and the command is still running
                    t.c.cancel();
                }
            }
        }
    }

    enum TriggerType {
        PRESS,
        RELEASE,
        WHILE_HELD
    }

    class Trigger {
        public Command c;
        public Button b;
        public TriggerType t;

        public Trigger(Command c, Button b, TriggerType t) {
            this.c = c;
            this.b = b;
            this.t = t;
        }
    }
}
