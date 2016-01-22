package com.explodingbacon.bcnlib.examples;

import com.explodingbacon.bcnlib.framework.Command;

public class ExampleCommand extends Command {

    @Override
    public void onInit() {
        System.out.println("Oink Oink...");
    }

    @Override
    public void onLoop() {}

    @Override
    public void onStop() {
        System.out.println("Boom!");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
