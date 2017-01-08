package com.explodingbacon.bcnlib.framework;

import com.explodingbacon.bcnlib.controllers.Button;
import com.explodingbacon.bcnlib.networking.NetButton;
import com.explodingbacon.bcnlib.networking.NetJoystick;
import com.explodingbacon.bcnlib.networking.NetTable;
import com.explodingbacon.bcnlib.networking.TableInterface;
import com.explodingbacon.bcnlib.utils.CodeThread;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that handles spawning commands based off of button inputs.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
 */

public abstract class AbstractOI extends CodeThread {

    private static List<Trigger> triggers = new ArrayList<>();
    private static List<Subsystem> disabledSubsystems = new ArrayList<>();
    private static List<Boolean> prevButtonValues = new ArrayList<>();

    private static final Object TRIGGERS_RW = new Object();
    private static final Object BUTTONS_RW = new Object();
    private static final Object JOYSTICKS_RW = new Object();

    public static TableInterface table = new NetTable("Robot_OI");
    private static List<NetButton> netButtons = new ArrayList<>();
    private static List<NetJoystick> netJoysticks = new ArrayList<>();

    /**
     * Makes a command run when a button is pressed.
     *
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whenPressed(Button b, Command c) {
        addTrigger(new Trigger(c, b, TriggerType.PRESS));
    }

    /**
     * Makes a command run when a button is released.
     *
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whenReleased(Button b, Command c) {
        addTrigger(new Trigger(c, b, TriggerType.RELEASE));
    }

    /**
     * Makes a command run while a button is held. If the command terminates and the button is still held, the command will start again.
     *
     * @param b The button to trigger the command.
     * @param c The command to be run.
     */
    public void whileHeld(Button b, Command c) {
        addTrigger(new Trigger(c, b, TriggerType.WHILE_HELD));
    }

    /**
     * Adds a command to the trigger list and runs it. This is used for when you just want to run a command somewhere in the code
     * without worrying about keeping the command object around.
     *
     * @param c The command to be run.
     */
    public static void runCommand(Command c) {
        addTrigger(new Trigger(c, null, TriggerType.NONE));
        c.start();
    }

    /**
     * Adds multiple commands to the trigger list and runs them. This is used for when you just want to run commands somewhere in the code
     * without worrying about keeping their objects around.
     *
     * @param cs The commands to be run.
     */
    public static void runCommands(Command... cs) {
        for (Command c : cs) {
            addTrigger(new Trigger(c, null, TriggerType.NONE));
            c.start();
        }
    }

    /**
     * Deletes all the Command triggers and stops any Commands they may have started.
     */
    public static void deleteAllTriggers() {
        synchronized (TRIGGERS_RW) {
            for (Trigger t : triggers) {
                t.c.forceStop();
                Log.d("Force stopped " + t.c.getClass().getSimpleName());
            }
            triggers.clear();
        }
    }

    /**
     * Disables a Subsystem, stopping all its running Commands and preventing new ones from being started.
     *
     * @param s The Subsystem to be disabled.
     */
    public static void disableSubsystem(Subsystem s) {
        disabledSubsystems.add(s);
    }

    /**
     * Adds a NetButton to the update list.
     *
     * @param b The NetButton to be added.
     */
    public static synchronized void addNetButton(NetButton b) {
        synchronized (BUTTONS_RW) {
            netButtons.add(b);
        }
    }

    /**
     * Adds a NetJoystick to the update list.
     *
     * @param j The NetButton to be added.
     */
    public static synchronized void addNetJoystick(NetJoystick j) {
        synchronized (JOYSTICKS_RW) {
            netJoysticks.add(j);
        }
    }

    /**
     * Adds a Trigger to the update list.
     *
     * @param t The Trigger to be added.
     */
    private static synchronized void addTrigger(Trigger t) {
        synchronized (TRIGGERS_RW) {
            triggers.add(t);
        }
    }

    @Override
    public void code() {
        synchronized (JOYSTICKS_RW) {
            netJoysticks.forEach(NetJoystick::refresh);
        }
        synchronized (BUTTONS_RW) {
            netButtons.forEach(NetButton::refresh);
        }
        synchronized (TRIGGERS_RW) {
            int index = 0;
            List<Trigger> triggersToRemove = new ArrayList<>();
            for (Trigger t : triggers) {
                if (t.c.requiredSub == null || !disabledSubsystems.contains(t.c.requiredSub)) {
                    if (t.t == TriggerType.PRESS) {
                        if (getButtonPrevious(index, t.b) && t.b.get()) { //If the button wasn't pressed before and now is
                            t.c.start();
                        }
                    } else if (t.t == TriggerType.RELEASE) {
                        if (getButtonPrevious(index, t.b) && !t.b.get()) { //If the button was pressed before and now isn't
                            t.c.start();
                        }
                    } else if (t.t == TriggerType.WHILE_HELD) {
                        if (t.b.get() && !t.c.isRunning()) { //If the button is pressed and the command isn't started already
                            t.c.start();
                        } else if (!t.b.get() && t.c.isRunning()) { //If the button is released and the command is still running
                            t.c.forceStop();
                        }
                    } else if (t.t == TriggerType.NONE) {
                        if (!t.c.isRunning()) { //This command will never be restarted, so it will be deleted from the trigger list
                            triggersToRemove.add(t);
                        }
                    }
                } else if (t.c.requiredSub != null) {
                    if (t.c.isRunning()) {
                        t.c.forceStop();
                    }
                }
                index++;
            }
            for (Trigger t : triggersToRemove) {
                triggers.remove(t);
            }
            prevButtonValues.clear();
            for (Trigger t : triggers) {
                if (t.t != TriggerType.NONE) {
                    prevButtonValues.add(t.b.get());
                }
            }
        }
    }

    public boolean getButtonPrevious(int index, Button b) {
        if (prevButtonValues.size() > index) {
            return prevButtonValues.get(index);
        } else {
            return false;
        }
    }

    public enum TriggerType {
        PRESS,
        RELEASE,
        WHILE_HELD,

        NONE
    }

    protected static class Trigger {
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
