package com.explodingbacon.bcnlib.framework;

public interface PIDSource {
    double getForPID();

    void reset();
}
