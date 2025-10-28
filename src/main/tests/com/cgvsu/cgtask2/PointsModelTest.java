import com.cgvsu.cgtask2.models.PointsModel;
import javafx.geometry.Point2D;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointsModelTest {
    @Test
    public void testAddTwoPoints() {
        PointsModel points = new PointsModel();
        points.add(1, 2);
        points.add(3, 4);
        assertEquals(2, points.size());
        assertEquals(new Point2D(1, 2), points.get(0));
        assertEquals(new Point2D(3, 4), points.get(1));
    }

    @Test
    public void testReplaceOldestPoint() {
        PointsModel points = new PointsModel();
        points.add(1, 1);
        points.add(2, 2);
        points.add(3, 3); // заменяет (1,1)
        assertEquals(new Point2D(2, 2), points.get(0));
        assertEquals(new Point2D(3, 3), points.get(1));
    }

    @Test
    public void testSizeLimit() {
        PointsModel points = new PointsModel();
        points.add(1, 1);
        points.add(2, 2);
        points.add(3, 3);
        assertEquals(2, points.size());
    }
}
