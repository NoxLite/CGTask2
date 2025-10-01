package com.cgvsu.cgtask2;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;

public class ProtoCurveController {
    @FXML
    public SplitPane splitPane;

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    ArrayList<Point2D> points = new ArrayList<Point2D>();
    Line line;

    int r1 = 168;
    int g1 = 50;
    int b1 = 50;

    int r2 = 168;
    int g2 = 50;
    int b2 = 153;


    @FXML
    private void initialize() {
        splitPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        splitPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        drawCircle(canvas.getGraphicsContext2D(), 300);
        line = new Line(canvas.getWidth() / 2, canvas.getHeight() / 2, 0, 0);

        canvas.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY -> {
                    handlePrimaryClick(canvas.getGraphicsContext2D(), event, 300);
                    canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
                    drawCircle(canvas.getGraphicsContext2D(), 300);
                    pointsDraw(canvas.getGraphicsContext2D(), 300);
                    if (points.size() == 2) {
                        drawSector(canvas.getGraphicsContext2D(), 300);
                    }
                }
            }
        });

    }


    private void drawCircle(GraphicsContext graphicsContext, double radius) {
        graphicsContext.strokeOval(canvas.getWidth() / 2 - radius / 2,
                canvas.getHeight() / 2 - radius / 2, radius, radius);
        graphicsContext.fillOval(canvas.getWidth() / 2 - 2.5, canvas.getHeight() / 2 - 2.5, 5, 5);
        graphicsContext.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void pointsDraw(GraphicsContext graphicsContext, double radius) {
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        for (Point2D point : points) {
            double dx = point.getX() - centerX;
            double dy = point.getY() - centerY;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double endX = centerX + (dx / distance) * radius / 2;
            double endY = centerY + (dy / distance) * radius / 2;
            graphicsContext.strokeLine(centerX, centerY, endX, endY);
        }
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event, int radius) {
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        double dx = event.getX() - centerX;
        double dy = event.getY() - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double endX = centerX + (dx / distance) * radius / 2;
        double endY = centerY + (dy / distance) * radius / 2;

        final Point2D clickPoint = new Point2D(endX, endY);
        if (points.size() < 2) {
            points.add(clickPoint);
        } else {
            points.set(0, points.get(1));
            points.set(1, clickPoint);
        }
    }

    private void drawSector(GraphicsContext graphicsContext, double radius) {
        boolean inSector = false;

        double startX = canvas.getWidth() / 2 - radius / 2;
        double startY = canvas.getHeight() / 2 - radius / 2;
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        double ax = points.get(0).getX() - centerX;
        double ay = points.get(0).getY() - centerY;
        double bx = points.get(1).getX() - centerX;
        double by = points.get(1).getY() - centerY;

        double crossAB = ax * by - ay * bx;


        for (double x = startX; x < (int) (startX + radius) ; x+=0.4) {
            for (double y = startY; y < (int) (startY + radius); y+=0.4) {
                double px = x - centerX;
                double py = y - centerY;
                if (px * px + py * py <= radius * radius / 4) {
                    double crossA = ax * py - ay * px;
                    double crossB = bx * py - by * px;


                    if (crossAB >= 0) {
                        inSector = crossA >= 0 && crossB <= 0;

                    } else {
                        inSector = crossA <= 0 && crossB >= 0;

                    }
                    if (inSector) {
                        double dx = x - centerX;
                        double dy = y - centerY;
                        double d = Math.sqrt(dx * dx + dy * dy);
                        double endX = centerX + (dx / d) * radius / 2;
                        double endY = centerY + (dy / d) * radius / 2;


                        double delta = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) /
                                Math.sqrt((endX - centerX) * (endX - centerX) + (endY - centerY) * (endY - centerY));

                        float rnew = (float) ((r1 + delta * (r2 - r1)) / 255.0);
                        float gnew = (float) ((g1 + delta * (g2 - g1)) / 255.0);
                        float bnew = (float) ((b1 + delta * (b2 - b1)) / 255.0);
                        graphicsContext.setFill(new Color(rnew , gnew , bnew, 1.0));
                        graphicsContext.fillRect(x, y, 1, 1);
                    }
                }
            }
        }
    }
}