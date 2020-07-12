package pointset;

import java.util.LinkedList;
import java.util.List;

/**
 * Naive nearest neighbor implementation using a linear scan.
 */
public class NaivePointSet implements PointSet {
    private List<Point> naivePS;

    /**
     * Instantiates a new NaivePointSet with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public NaivePointSet(List<Point> points) {
        naivePS = new LinkedList<>();
        for (Point p : points) {
            Point pCopy = new Point(p.x(), p.y());
            naivePS.add(pCopy);
        }
    }

    /**
     * Returns the point in this set closest to (x, y) in O(N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        // point that is closest to x, y from the naivePS.
        Point ret = null;
        double minDistance = naivePS.get(0).distanceSquaredTo(x, y); //just set this to the first element in list
        for (Point p : naivePS) {
            double distance = p.distanceSquaredTo(x, y);
            if (distance < minDistance) {
                minDistance = distance;
                ret = p;
            }
        }
        return ret;
    }
}
