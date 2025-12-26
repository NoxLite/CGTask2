package com.cgvsu.cgtask2.models;

import javafx.geometry.Point2D;

public interface InterpolationInterface {
      default double getInterpolation(Point2D startP, Point2D endP, Point2D currentP) {
        return 0;
    }

}
