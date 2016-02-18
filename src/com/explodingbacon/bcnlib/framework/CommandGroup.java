package com.explodingbacon.bcnlib.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ryan Shavell
 * @version 2016.2.1
 */

public class CommandGroup extends Command {

    private List<Command> commands = new ArrayList<>();

    /**
     * Creates an empty CommandGroup
     */
    public CommandGroup(){}

    /**
     * Creates a CommandGroup consisting of the Commands passed as arguments.
     * @param c The Commands to be in the CommandGroup.
     */
    public CommandGroup(Command... c) {
        Collections.addAll(commands, c);
    }

    /**
     * Adds a Command to this CommandGroup.
     * @param c The Command to be added.
     * @return This CommandGroup.
     */
    public CommandGroup add(Command... c) {
        Collections.addAll(commands, c);
        return this;
    }

    @Override
    public void onInit() {
        for (Command c : commands) {
            AbstractOI.runCommand(c);
            c.waitTillFinished();
        }
    }

    @Override
    public void onLoop() {}

    @Override
    public void onStop() {}

    @Override
    public boolean isFinished() { return true; }
}
