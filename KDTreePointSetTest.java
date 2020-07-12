package pointset;

import org.junit.Test;

import java.util.List;

public class KDTreePointSetTest {
    @Test
    public void testConstructor() {
        Point p1 = new Point(2, 3); // Point with x = 1.1, y = 2.2
        Point p2 = new Point(1, 5);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(4, 1);
        Point p6 = new Point(3, 3);
        Point p7 = new Point(4, 4);

        PointSet tt = new KDTreePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));

        double x = 3.0;
        double y = 4.0;           // Mouse-click at (3, 4)
        Point ret = tt.nearest(x, y);   // ret == p2
    }
}
