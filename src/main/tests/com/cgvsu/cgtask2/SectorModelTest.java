import com.cgvsu.cgtask2.models.CircleModel;
import com.cgvsu.cgtask2.models.PointsModel;
import com.cgvsu.cgtask2.models.SectorModel;
import javafx.geometry.Point2D;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SectorModelTest {
    private CircleModel circle;
    private PointsModel points;

    @Before
    public void setup() {
        circle = new CircleModel(100, 0, 0);
        points = new PointsModel();
    }

    @Test
    public void testPointInsideSector() {
        points.add(50, 0);
        points.add(0, 50);
        SectorModel sector = new SectorModel(points, circle);

        assertTrue(sector.checkPointInSector(new Point2D(30, 30)));
    }

    @Test
    public void testPointOutsideSector() {
        points.add(50, 0);
        points.add(0, 50);
        SectorModel sector = new SectorModel(points, circle);

        assertFalse(sector.checkPointInSector(new Point2D(30, -30)));
    }

    @Test
    public void testPointOutsideCircle() {
        points.add(50, 0);
        points.add(0, 50);
        SectorModel sector = new SectorModel(points, circle);

        assertFalse(sector.checkPointInSector(new Point2D(80, 80)));
    }

    @Test
    public void testReversePointsOrder() {
        points.add(0, 50);
        points.add(50, 0);
        SectorModel sector = new SectorModel(points, circle);

        assertTrue(sector.checkPointInSector(new Point2D(30, 30)));
    }
}
