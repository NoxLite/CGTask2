package com.cgvsu.cgtask2.models;

import javafx.geometry.Point2D;

public class CircleModel {
    private final int diameter;

    private final Point2D center;

    public CircleModel(int diameter, int x, int y) {
        this.diameter = diameter;
        this.center = new Point2D(x, y);
    }

    public double getDiameter() {
        return diameter;
    }

    public Point2D getCenter() {
        return center;
    }

    public boolean contains(Point2D point) {
        double dx = point.getX() - center.getX();
        double dy = point.getY() - center.getY();
        return dx * dx + dy * dy <= (diameter / 2) * (diameter / 2);
    }
}
