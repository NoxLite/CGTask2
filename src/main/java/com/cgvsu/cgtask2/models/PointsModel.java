package com.cgvsu.cgtask2.models;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PointsModel {

    private final List<Point2D> points = new ArrayList<>();


    public void add(Point2D point) {
        if (points.size() < 2) {
            points.add(point);
        } else {
            points.set(0, points.get(1));
            points.set(1, point);
        }
    }

    public void add(double x, double y) {
        add(new Point2D(x, y));
    }

    public Point2D get(int index) {
        return points.get(index);
    }

    public int size() {
        return points.size();
    }
}
