package com.cgvsu.cgtask2.view;

import com.cgvsu.cgtask2.models.CircleModel;
import com.cgvsu.cgtask2.models.InterpolationModel;
import com.cgvsu.cgtask2.models.PointsModel;
import com.cgvsu.cgtask2.models.SectorModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Objects;

public class CircleView {

    private final Canvas canvas;
    private final CircleModel circleModel;
    private final GraphicsContext graphicsContext;
    private final PointsModel pointsModel;
    private final SectorModel sectorModel;


    private Color color1;
    private Color color2;

    public CircleView(Canvas canvas, CircleModel circleModel, PointsModel pointsModel, SectorModel sectorModel, Color color1, Color color2) {
        this.canvas = canvas;
        this.circleModel = circleModel;
        this.pointsModel = pointsModel;
        this.sectorModel = sectorModel;
        this.color1 = color1;
        this.color2 = color2;

        graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(event -> {
                if (Objects.requireNonNull(event.getButton()) == MouseButton.PRIMARY) {
                handlePrimaryClick(event);
                redraw();
            }
        });

    }

    public void handlePrimaryClick(MouseEvent event) {
        double dx = event.getX() - circleModel.getCenter().getX();
        double dy = event.getY() - circleModel.getCenter().getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        double endX = circleModel.getCenter().getX() + (dx / distance) * circleModel.getDiameter() / 2;
        double endY = circleModel.getCenter().getY() + (dy / distance) * circleModel.getDiameter() / 2;

        pointsModel.add(endX, endY);
    }

    public void changeColor1(Color color) {
        color1 = color;
    }

    public void changeColor2(Color color) {
        color2 = color;
    }

    public void redraw() {
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        draw();
    }

    public void draw() {
        graphicsContext.strokeOval(canvas.getWidth() / 2 - circleModel.getDiameter() / 2,
                canvas.getHeight() / 2 - circleModel.getDiameter() / 2, circleModel.getDiameter(), circleModel.getDiameter());
        graphicsContext.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (pointsModel.size() == 1) {
            graphicsContext.strokeLine(
                    circleModel.getCenter().getX(),
                    circleModel.getCenter().getY(),
                    pointsModel.get(0).getX(),
                    pointsModel.get(0).getY());
        }
        if (pointsModel.size() == 2) {

            for (int x = (int) (circleModel.getCenter().getX() - circleModel.getDiameter() / 2);
                 x < (int) (circleModel.getCenter().getX() + circleModel.getDiameter() / 2) ; x++) {
                for (int y = (int) (circleModel.getCenter().getY() - circleModel.getDiameter() / 2);
                     y < (int) (circleModel.getCenter().getY() + circleModel.getDiameter() / 2) ; y++) {
                    if (sectorModel.checkPointInSector(new Point2D(x, y))) {

                        double dx = x - circleModel.getCenter().getX();
                        double dy = y - circleModel.getCenter().getY();
                        double d = Math.sqrt(dx * dx + dy * dy);
                        double endX = circleModel.getCenter().getX() + (dx / d) * circleModel.getDiameter() / 2;
                        double endY = circleModel.getCenter().getY() + (dy / d) * circleModel.getDiameter() / 2;

                        double delta = InterpolationModel.getInterpolation(circleModel.getCenter(), new Point2D(endX, endY), new Point2D(x, y));

                        double rnew = color1.getRed() + delta * (color2.getRed() - color1.getRed());
                        double gnew = color1.getGreen() + delta * (color2.getGreen() - color1.getGreen());
                        double bnew = color1.getBlue() + delta * (color2.getBlue() - color1.getBlue());

                        graphicsContext.getPixelWriter().setColor(x, y, new Color(rnew , gnew , bnew, 1.0));
                        /* graphicsContext.setFill(new Color(rnew , gnew , bnew, 1.0));
                        graphicsContext.fillRect(x, y, 1, 1); */
                    }
                }
            }
        }
    }
}
