package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.utils.CodeThread;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles spawning commands based off of button inputs.
 *
 * @author Ryan Shavell
 * @version 2016.1.18
 */

public abstract class ExtendableOI extends CodeThread {

    private static List<Trigger> triggers = new ArrayList<>();
    public static NetTable netTable = new NetTable("Robot_OI");
    private static List<NetButton> netButtons = new ArrayList<>();
    private static List<NetJoystick> netJoysticks = new ArrayList<>();
    private static final Object TRIGGERS_EDIT = new Object();

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
     * Adds a command to the trigger list and runs it. This is used for when you just want to run a command somewhere in the code
     * without worrying about keeping the command object around.
     * @param c The command to be run.
     */
    public static void runCommand(Command c) {
        addTrigger(new Trigger(c, null, TriggerType.NOTHING));
        c.start();
    }

    /**
     * Adds multiple commands to the trigger list and runs them. This is used for when you just want to run commands somewhere in the code
     * without worrying about keeping their objects around.
     * @param cs The commands to be run.
     */
    public static void runCommands(Command... cs) {
        for (Command c : cs) {
            addTrigger(new Trigger(c, null, TriggerType.NOTHING));
            c.start();
        }
    }

    public static void addNetButton(NetButton b) { netButtons.add(b); }

    public static void addNetJoystick(NetJoystick j) {
        netJoysticks.add(j);
    }

    private static synchronized void addTrigger(Trigger t) {
        synchronized (TRIGGERS_EDIT) {
            triggers.add(t);
        }
    }

    @Override
    public void code() {
        for (NetJoystick j : netJoysticks) {
            j.refresh();
        }

        for (NetButton b : netButtons) {
            b.refresh();
        }
        synchronized (TRIGGERS_EDIT) {
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
