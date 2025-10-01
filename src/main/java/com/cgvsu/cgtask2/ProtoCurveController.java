package com.cgvsu.cgtask2;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;
import java.util.Objects;

public class ProtoCurveController {
    @FXML
    public SplitPane splitPane;
    public ColorPicker colorPicker1;
    public ColorPicker colorPicker2;

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    private ArrayList<Point2D> points = new ArrayList<Point2D>();

    private double r1 = 168 / 255.0;
    private double g1 = 50 / 255.0;
    private double b1 = 50 / 255.0;

    private double r2 = 168 / 255.0;
    private double g2 = 50 / 255.0;
    private double b2 = 153 / 255.0;

    private final static double DIAMETER = 300;

    @FXML
    private void initialize() {
        splitPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        splitPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        drawCircle(canvas.getGraphicsContext2D());

        colorPicker1.setValue(new Color(r1, g1, b1, 1.0));
        colorPicker2.setValue(new Color(r2, g2, b2, 1.0));

        canvas.setOnMouseClicked(event -> {
            if (Objects.requireNonNull(event.getButton()) == MouseButton.PRIMARY) {
                handlePrimaryClick(canvas.getGraphicsContext2D(), event);
                redraw();
            }
        });

        colorPicker1.valueProperty().addListener((observableValue, color, t1) -> {
                r1 = colorPicker1.getValue().getRed();
                g1 = colorPicker1.getValue().getGreen();
                b1 = colorPicker1.getValue().getBlue();
                redraw();
            }
        );

        colorPicker2.valueProperty().addListener((observableValue, color, t1) -> {
                    r2 = colorPicker2.getValue().getRed();
                    g2 = colorPicker2.getValue().getGreen();
                    b2 = colorPicker2.getValue().getBlue();
                    redraw();
                }
        );
    }

    private void redraw() {
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        drawCircle(canvas.getGraphicsContext2D());
        if (points.size() == 2) {
            drawSector(canvas.getGraphicsContext2D());
        }
        pointsDraw(canvas.getGraphicsContext2D());
    }

    private void drawCircle(GraphicsContext graphicsContext) {
        graphicsContext.strokeOval(canvas.getWidth() / 2 - DIAMETER / 2,
                canvas.getHeight() / 2 - DIAMETER / 2, DIAMETER, DIAMETER);
        graphicsContext.fillOval(canvas.getWidth() / 2 - 2.5, canvas.getHeight() / 2 - 2.5, 5, 5);
        graphicsContext.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void pointsDraw(GraphicsContext graphicsContext) {
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        for (Point2D point : points) {
            double dx = point.getX() - centerX;
            double dy = point.getY() - centerY;
            double distance = Math.sqrt(dx * dx + dy * dy);
            double endX = centerX + (dx / distance) * DIAMETER / 2;
            double endY = centerY + (dy / distance) * DIAMETER / 2;
            graphicsContext.strokeLine(centerX, centerY, endX, endY);
        }
    }

    private void handlePrimaryClick(GraphicsContext graphicsContext, MouseEvent event) {
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        double dx = event.getX() - centerX;
        double dy = event.getY() - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double endX = centerX + (dx / distance) * DIAMETER / 2;
        double endY = centerY + (dy / distance) * DIAMETER / 2;

        final Point2D clickPoint = new Point2D(endX, endY);
        if (points.size() < 2) {
            points.add(clickPoint);
        } else {
            points.set(0, points.get(1));
            points.set(1, clickPoint);
        }
    }

    private void drawSector(GraphicsContext graphicsContext) {
        boolean inSector = false;

        double startX = canvas.getWidth() / 2 - DIAMETER / 2;
        double startY = canvas.getHeight() / 2 - DIAMETER / 2;
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        double ax = points.get(0).getX() - centerX;
        double ay = points.get(0).getY() - centerY;
        double bx = points.get(1).getX() - centerX;
        double by = points.get(1).getY() - centerY;

        double crossAB = ax * by - ay * bx;


        for (double x = startX; x < (int) (startX + DIAMETER) ; x+=0.4) {
            for (double y = startY; y < (int) (startY + DIAMETER); y+=0.4) {
                double px = x - centerX;
                double py = y - centerY;
                if (px * px + py * py <= DIAMETER * DIAMETER / 4) {
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
                        double endX = centerX + (dx / d) * DIAMETER / 2;
                        double endY = centerY + (dy / d) * DIAMETER / 2;


                        double delta = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) /
                                Math.sqrt((endX - centerX) * (endX - centerX) + (endY - centerY) * (endY - centerY));

                        double rnew = r1 + delta * (r2 - r1);
                        double gnew = g1 + delta * (g2 - g1);
                        double bnew = b1 + delta * (b2 - b1);
                        graphicsContext.setFill(new Color(rnew , gnew , bnew, 1.0));
                        graphicsContext.fillRect(x, y, 1, 1);
                    }
                }
            }
        }
    }
}