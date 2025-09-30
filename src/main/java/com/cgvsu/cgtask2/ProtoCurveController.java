package com.cgvsu.cgtask2;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class ProtoCurveController {
    @FXML
    public SplitPane splitPane;
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    ArrayList<Point2D> points = new ArrayList<Point2D>();

    @FXML
    private void initialize() {
        splitPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        splitPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        drawCircle(canvas.getGraphicsContext2D(), 300);
        canvas.setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY -> handlePrimaryClick(canvas.getGraphicsContext2D(), event);
            }
        });

        canvas.setOnMouseMoved(mouseEvent -> {
            canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
            drawCircle(canvas.getGraphicsContext2D(), 300);
            drawRadius(canvas.getGraphicsContext2D(), 300, mouseEvent);
        });
    }

    private void drawRadius(GraphicsContext graphicsContext, double radius, MouseEvent mouseEvent) {

        double xCenter = canvas.getWidth() / 2;
        double yCenter = canvas.getHeight() / 2;
        double xMouse = mouseEvent.getX();
        double yMouse = mouseEvent.getY();

        double angle = Math.atan2(xMouse - xCenter, yMouse - yCenter);
        double xEnd = xCenter - radius * Math.cos(180 - angle);
        double yEnd = yCenter - radius * Math.sin(180 - angle);
        graphicsContext.strokeLine(xCenter, yCenter, xEnd, yEnd);
    }

    private void drawCircle(GraphicsContext graphicsContext, double radius) {
        graphicsContext.strokeOval(canvas.getWidth() / 2 - radius / 2,
                canvas.getHeight() / 2 - radius / 2, radius, radius);
        graphicsContext.fillOval(canvas.getWidth() / 2 - 2.5, canvas.getHeight() / 2 - 2.5, 5, 5);
        graphicsContext.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        final Point2D clickPoint = new Point2D(event.getX(), event.getY());

        final int POINT_RADIUS = 3;
        graphicsContext.fillOval(
                clickPoint.getX() - POINT_RADIUS, clickPoint.getY() - POINT_RADIUS,
                2 * POINT_RADIUS, 2 * POINT_RADIUS);

        if (points.size() > 0) {
            final Point2D lastPoint = points.get(points.size() - 1);
            graphicsContext.strokeLine(lastPoint.getX(), lastPoint.getY(), clickPoint.getX(), clickPoint.getY());
        }
        points.add(clickPoint);
    }
}