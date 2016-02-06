package com.explodingbacon.bcnlib.controllers;

/**
 * An enum  for DPad directions on GameControllers.
 *
 * @author Ryan Shavell
 * @version 2016.2.6
 */

public enum Direction {
    UP(0),
    UP_RIGHT(45),
    RIGHT(90),
    DOWN_RIGHT(135),
    DOWN(180),
    DOWN_LEFT(225),
    LEFT(270),
    UP_LEFT(315),

    NONE(-1);

    public static Direction[] allDirections = new Direction[]{Direction.UP, Direction.UP_RIGHT, Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN, Direction.DOWN_LEFT, Direction.LEFT, Direction.UP_LEFT, Direction.NONE};
    public int angle;

    Direction(int angle) {
        this.angle = angle;
    }

    public boolean isUp() {
        return (this == Direction.UP_LEFT || this == Direction.UP || this == Direction.UP_RIGHT);
    }

    public boolean isRight() {
        return (this == Direction.UP_RIGHT || this == Direction.RIGHT || this == Direction.DOWN_RIGHT);
    }

    public boolean isDown() {
        return (this == Direction.DOWN_LEFT || this == Direction.DOWN || this == Direction.DOWN_RIGHT);
    }

    public boolean isLeft() {
        return (this == Direction.UP_LEFT || this == Direction.LEFT || this == Direction.DOWN_LEFT);
    }

    public static Direction toDirection(int angle) {
        for (Direction d : allDirections) {
            if (d.angle == angle) {
                return d;
            }
        }
        return Direction.NONE;
    }
}
