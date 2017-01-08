package com.explodingbacon.bcnlib.controllers;

/**
 * An enum for DPad directions on GameControllers.
 *
 * @author Ryan Shavell
 * @version 2016.3.2
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

    public int angle;

    Direction(int angle) {
        this.angle = angle;
    }

    /**
     * Checks if the up button on the DPad is pressed.
     *
     * @return If the up button on the DPad is pressed.
     */
    public boolean isUp() {
        return (this == Direction.UP_LEFT || this == Direction.UP || this == Direction.UP_RIGHT);
    }

    /**
     * Checks if the right button on the DPad is pressed.
     *
     * @return If the right button on the DPad is pressed.
     */
    public boolean isRight() {
        return (this == Direction.UP_RIGHT || this == Direction.RIGHT || this == Direction.DOWN_RIGHT);
    }

    /**
     * Checks if the down button on the DPad is pressed.
     *
     * @return If the down button on the DPad is pressed.
     */
    public boolean isDown() {
        return (this == Direction.DOWN_LEFT || this == Direction.DOWN || this == Direction.DOWN_RIGHT);
    }

    /**
     * Checks if the left button on the DPad is pressed.
     *
     * @return If the left button on the DPad is pressed.
     */
    public boolean isLeft() {
        return (this == Direction.UP_LEFT || this == Direction.LEFT || this == Direction.DOWN_LEFT);
    }

    /**
     * Converts an angle to a Direction.
     *
     * @param angle The angle.
     * @return The Direction.
     */
    public static Direction toDirection(int angle) {
        for (Direction d : Direction.values()) {
            if (d.angle == angle) {
                return d;
            }
        }
        return Direction.NONE;
    }
}
