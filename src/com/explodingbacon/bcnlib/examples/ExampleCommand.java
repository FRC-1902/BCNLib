package com.explodingbacon.bcnlib.examples;

import com.explodingbacon.bcnlib.framework.Command;

public class ExampleCommand extends Command {

    @Override
    public void init() {
        System.out.println("Oink Oink...");
    }

    @Override
    public void loop() {}

    @Override
    public void stop() {
        System.out.println("Boom!");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
