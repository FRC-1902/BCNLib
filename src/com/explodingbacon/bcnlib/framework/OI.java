package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.utils.CodeThread;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles spawning commands based off of button inputs.
 *
 * @author Ryan Shavell
 * @version 2016.1.14
 */

public abstract class OI extends CodeThread {

    private static List<Trigger> triggers = new ArrayList<>();
    public static NetTable netTable = new NetTable("Robot_OI");
    private static List<NetButton> netButtons = new ArrayList<>();
    private static List<NetJoystick> netJoysticks = new ArrayList<>();

    /**
     * Makes a command run when a button is pressed.
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whenPressed(Button b, Command c) {
        addTrigger(new Trigger(c, b, TriggerType.PRESS));
    }

    /**
     * Makes a command run when a button is released.
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whenReleased(Button b, Command c) {
        addTrigger(new Trigger(c, b, TriggerType.RELEASE));
    }

    /**
     * Makes a command run while a button is held. If the command terminates and the button is still held, the command will start again.
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whileHeld(Button b, Command c) {
        addTrigger(new Trigger(c, b, TriggerType.WHILE_HELD));
    }

    /**
     * Adds a command to the trigger list. This is used for when you just want to run a command somewhere in the code
     * without worrying about keeping the command object around.
     *
     * @param c The command to be added.
     * @return The command you added (for method chaining)
     */
    public static Command addCommand(Command c) {
        addTrigger(new Trigger(c, null, TriggerType.NOTHING));
        return c;
    }

    public static synchronized void addNetButton(NetButton b) { netButtons.add(b); }

    public static synchronized void addNetJoystick(NetJoystick j) {
        netJoysticks.add(j);
    }

    private static synchronized void addTrigger(Trigger t) {
        triggers.add(t);
    }

    @Override
    public void code() {
        for (NetJoystick j : netJoysticks) {
            j.refresh();
        }

        for (NetButton b : netButtons) {
            b.refresh();
        }
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

    public enum TriggerType {
        PRESS,
        RELEASE,
        WHILE_HELD,

        NOTHING
    }

    public static class Trigger {
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
