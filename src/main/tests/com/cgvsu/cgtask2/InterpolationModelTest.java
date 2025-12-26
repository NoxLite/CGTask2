package com.cgvsu.cgtask2;

import com.cgvsu.cgtask2.models.InterpolationModel;
import javafx.geometry.Point2D;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InterpolationModelTest {
    @Test
    public void testInterpolationAtStart() {
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(10, 0);
        Point2D current = new Point2D(0, 0);
        assertEquals(0.0, new InterpolationModel().getInterpolation(start, end, current), 1e-6);
    }

    @Test
    public void testInterpolationAtEnd() {
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(10, 0);
        Point2D current = new Point2D(10, 0);
        assertEquals(1.0, new InterpolationModel().getInterpolation(start, end, current), 1e-6);
    }

    @Test
    public void testInterpolationHalfway() {
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(10, 0);
        Point2D current = new Point2D(5, 0);
        assertEquals(0.5, new InterpolationModel().getInterpolation(start, end, current), 1e-6);
    }
}
