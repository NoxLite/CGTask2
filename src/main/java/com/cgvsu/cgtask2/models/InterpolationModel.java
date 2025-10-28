package com.cgvsu.cgtask2.models;

import javafx.geometry.Point2D;

public class InterpolationModel {
    public static double getInterpolation(Point2D startP, Point2D endP, Point2D currentP) {
        double coeff = Math.sqrt((currentP.getX() - startP.getX()) * (currentP.getX() - startP.getX()) + (currentP.getY() - startP.getY()) * (currentP.getY() - startP.getY()))
                / Math.sqrt((endP.getX() - startP.getX()) * (endP.getX() - startP.getX()) + (endP.getY() - startP.getY()) *  (endP.getY() - startP.getY()));
        return coeff;
    }
}
