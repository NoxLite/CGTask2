package com.cgvsu.cgtask2.models;

import javafx.geometry.Point2D;

public class SectorModel {

    private final PointsModel pointsModel;

    private final CircleModel circleModel;


    public SectorModel(PointsModel pointsModel, CircleModel circleModel) {
        this.pointsModel = pointsModel;
        this.circleModel = circleModel;
    }

    public boolean checkPointInSector(Point2D point) {
        if (pointsModel.size() != 2) {
            return false;
        }

        if (!circleModel.contains(point)) {
            return false;
        }

        double ax = pointsModel.get(0).getX() - circleModel.getCenter().getX();
        double ay = pointsModel.get(0).getY() - circleModel.getCenter().getY();
        double bx = pointsModel.get(1).getX() - circleModel.getCenter().getX();
        double by = pointsModel.get(1).getY() - circleModel.getCenter().getY();


        double crossAB = ax * by - ay * bx;
        double px = point.getX() - circleModel.getCenter().getX();
        double py = point.getY() - circleModel.getCenter().getY();
        if (px * px + py * py <= circleModel.getDiameter() * circleModel.getDiameter() / 4) {


            double crossA = ax * py - ay * px;
            double crossB = bx * py - by * px;

            if (crossAB >= 0) {
                return crossA >= 0 && crossB <= 0;
            }
            return crossA <= 0 && crossB >= 0;
        }
        return false;
    }
}
