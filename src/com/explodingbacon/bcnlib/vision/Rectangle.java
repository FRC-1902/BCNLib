package com.explodingbacon.bcnlib.vision;

import com.explodingbacon.bcnlib.utils.Utils;

public class Rectangle {

    public int x, y, width, height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = Utils.round(x);
        this.y = Utils.round(y);
        this.width = Utils.round(width);
        this.height = Utils.round(height);
    }

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
