package com.explodingbacon.bcnlib.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ryan Shavell
 * @version 2016.1.18
 */

public class CommandGroup extends Command {

    private List<Command> commands = new ArrayList<>();

    public CommandGroup(){}

    public CommandGroup(Command[] c) {
        Collections.addAll(commands, c);
    }

    public CommandGroup(List<Command> c) {
        commands = new ArrayList<>(c);
    }

    /**
     * Adds a Command to this CommandGroup.
     * @param c The Command to be added.
     * @return This CommandGroup.
     */
    public CommandGroup add(Command c) {
        commands.add(c);
        return this;
    }

    @Override
    public void init() {
        for (Command c : commands) {
            ExtendableOI.runCommand(c);
            c.waitTillFinished();
        }
    }

    @Override
    public void loop() {}

    @Override
    public void stop() {}

    @Override
    public boolean isFinished() { return true; }
}
