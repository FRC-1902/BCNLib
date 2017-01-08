package com.explodingbacon.bcnlib.utils;

/**
 * BCNLib implementation of Java.AWT's Point class.
 *
 * @author Ryan Shavell
 * @version 2016.9.10
 */
public class Point {

    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
