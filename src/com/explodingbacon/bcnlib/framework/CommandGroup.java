package com.explodingbacon.bcnlib.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ryan Shavell
 * @version 2016.1.13
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

    @Override
    public void init() {
        for (Command c : commands) {
            OI.addCommand(c).waitTillFinished();
        }
    }

    @Override
    public void loop() {}

    @Override
    public void stop() {}

    @Override
    public boolean isFinished() { return true; }
}
