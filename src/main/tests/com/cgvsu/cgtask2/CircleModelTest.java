import com.cgvsu.cgtask2.models.CircleModel;
import javafx.geometry.Point2D;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CircleModelTest {

    @Test
    public void testContainsInsidePoint() {
        CircleModel circle = new CircleModel(100, 0, 0);
        assertTrue(circle.contains(new Point2D(10, 10)));
    }

    @Test
    public void testContainsOutsidePoint() {
        CircleModel circle = new CircleModel(100, 0, 0);
        assertFalse(circle.contains(new Point2D(60, 60)));
    }

    @Test
    public void testContainsBoundaryPoint() {
        CircleModel circle = new CircleModel(100, 0, 0);
        assertTrue(circle.contains(new Point2D(50, 0)));
    }
}
