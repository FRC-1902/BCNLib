package com.explodingbacon.bcnlib.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ryan Shavell
 * @version 2016.1.20
 */

public class CommandGroup extends Command {

    private List<Command> commands = new ArrayList<>();

    public CommandGroup(){}

    public CommandGroup(Command... c) {
        Collections.addAll(commands, c);
    }

    /**
     * Adds a Command to this CommandGroup.
     * @param c The Command to be added.
     * @return This CommandGroup.
     */
    public CommandGroup add(Command... c) {
        for (Command com : c) {
            commands.add(com);
        }
        return this;
    }

    @Override
    public void onInit() {
        for (Command c : commands) {
            ExtendableOI.runCommand(c);
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
