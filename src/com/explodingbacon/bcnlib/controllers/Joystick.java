package com.explodingbacon.bcnlib.controllers;

public class Joystick extends edu.wpi.first.wpilibj.Joystick implements JoystickInterface {
    public Joystick(int port) {
        super(port);
    }
}