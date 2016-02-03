package com.explodingbacon.bcnlib.vision;

import org.opencv.core.MatOfPoint;
import java.util.ArrayList;
import java.util.List;

public class Contours {

    protected List<MatOfPoint> contours;

    protected Contours(List<MatOfPoint> con) {
        contours = new ArrayList<>(con);
    }

    public List<MatOfPoint> getContours() {
        return new ArrayList<>(contours);
    }
}
