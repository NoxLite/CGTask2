package com.cgvsu.cgtask2.controller;

import com.cgvsu.cgtask2.models.CircleModel;
import com.cgvsu.cgtask2.models.PointsModel;
import com.cgvsu.cgtask2.models.SectorModel;
import com.cgvsu.cgtask2.view.CircleView;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Objects;

public class Controller {

    @FXML
    public SplitPane splitPane;
    public ColorPicker colorPicker1;
    public ColorPicker colorPicker2;

    @FXML
    private Canvas canvas;

    private CircleView circleView;


    private double r1 = 168 / 255.0;
    private double g1 = 50 / 255.0;
    private double b1 = 50 / 255.0;

    private double r2 = 168 / 255.0;
    private double g2 = 50 / 255.0;
    private double b2 = 153 / 255.0;

    @FXML
    private void initialize() {
        splitPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        splitPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        // drawCircle(canvas.getGraphicsContext2D());

        colorPicker1.setValue(new Color(r1, g1, b1, 1.0));
        colorPicker2.setValue(new Color(r2, g2, b2, 1.0));

        CircleModel circleModel = new CircleModel(300, (int) canvas.getWidth() / 2, (int) canvas.getHeight() / 2);
        PointsModel pointsModel = new PointsModel();
        SectorModel sectorModel = new SectorModel(pointsModel, circleModel);

        circleView = new CircleView(canvas, circleModel, pointsModel, sectorModel, colorPicker1.getValue(), colorPicker2.getValue());

        circleView.draw();

        colorPicker1.valueProperty().addListener((observableValue, color, t1) -> {
                    r1 = colorPicker1.getValue().getRed();
                    g1 = colorPicker1.getValue().getGreen();
                    b1 = colorPicker1.getValue().getBlue();
                    circleView.changeColor1(colorPicker1.getValue());
                    circleView.redraw();
                }
        );

        colorPicker2.valueProperty().addListener((observableValue, color, t1) -> {
                    r2 = colorPicker2.getValue().getRed();
                    g2 = colorPicker2.getValue().getGreen();
                    b2 = colorPicker2.getValue().getBlue();
                    circleView.changeColor2(colorPicker2.getValue());
                    circleView.redraw();
                }
        );
    }
}
