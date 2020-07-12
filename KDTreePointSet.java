package pointset;

import java.util.*;

public class KDTreePointSet implements PointSet {
    private KDNode kDTreePointSet = null;
    private Point temp;

    /**
     * Instantiates a new KDTree with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public KDTreePointSet(List<Point> points) {
        if (points == null) {
            return;
        }
        if (points.isEmpty()) {
            return;
        }
        for (Point p : points) {
            Point pCopy = new Point(p.x(), p.y());
            add(pCopy);
        }
    }

    private void add(Point p) {
        //throw exception for p == null
        if (p == null) {
            return;
        }
        int depth = 0;
        kDTreePointSet = add(p, kDTreePointSet, depth, true);
    }

    /**
     * @param point : current point that is added into the tree
     * @param node  : parent node
     * @param depth : depth of current node.
     * @param level : true for even depth, false for odd depth
     * @return node
     */
    private KDNode add(Point point, KDNode node, int depth, boolean level) {
        if (node == null) { //if current node is null, return the new node of point and depth
            return new KDNode(point, depth);
        }
        if (level && point.x() < node.point.x()) {
            node.left = add(point, node.left, depth + 1, !level);
        } else if (level && point.x() >= node.point.x()) {
            node.right = add(point, node.right, depth + 1, !level);
        } else if (!level && point.y() < node.point.y()) {
            node.left = add(point, node.left, depth + 1, !level);
        } else if (!level && point.y() >= node.point.y()) {
            node.right = add(point, node.right, depth + 1, !level);
        }
        return node;
    }

    /**
     * Returns the point in this set closest to (x, y) in (usually) O(log N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        if (kDTreePointSet == null) { //if there's nothing in KDTree, no nearest point.
            return null;
        }
        //writing a nearest method that recursively traverses entire tree in Omega(N)
        Point point = new Point(x, y);
        KDNode query = new KDNode(point, 0);
        KDNode best = kDTreePointSet; // the best node in this case is the root
        return nearest(kDTreePointSet, query, best, true).point;
    }

    private KDNode nearest(KDNode node, KDNode query, KDNode best, boolean level) {
        KDNode goodSide;
        KDNode badSide;
        if (node == null) { //base case: if node is empty / leaf, return.
            return best;
        }
        if (node.point.distanceSquaredTo(query.point) < best.point.distanceSquaredTo(query.point)) {
            best = node;
        }
        if (level && query.point.x() < node.point.x() ||
                !level && query.point.y() < node.point.y()) {
            //whether point is in left side or right side. OR point is in up or down side.
            goodSide = node.left;
            badSide = node.right;
        } else {
            goodSide = node.right;
            badSide = node.left;
        }
        best = nearest(goodSide, query, best, !level);
        //bad side can have better neighbor -> comparison with parent's point
        if (level) {
            temp = new Point(node.point.x(), query.point.y());
        } else {
            temp = new Point(query.point.x(), node.point.y());
        }
        if (temp.distanceSquaredTo(query.point) < best.point.distanceSquaredTo(query.point)) {
            best = nearest(badSide, query, best, !level);
        }
        return best;
    }

    //level && point.y() < node.point.y() ||
    //                !level && point.x() < node.point.x()

    //what if a point is equidistant?

    /* finding the 'perpendicular' point (if splitting on x)
     *   1. copy the y coordinate of query point
     *   2. copy the x coordinate of the splitter point
     *   3. construct a 'fake' point
     *   4. check if a fake point exists in the set of all points
     *   5. if it exists, calculate the distance between the fake point and query point.
     *   6. if the distance is smaller than the distance wrt best side, return the node of fake point.
     */

    /* finding the 'perpendicular' point (if splitting on y)
     *  1. copy the x coordinate of the query point
     *  2. copy the y coordinate of the splitter point
     *
     */

    /*condition when bad side can have better neighbor
    private boolean condition(KDNode node, Point point, KDNode best, boolean level) {
        double yCopy;
        double xCopy;
        Point temp;
        if (level) {
            yCopy = point.y();
            xCopy = node.point.x();
            temp = new Point(xCopy, yCopy);
            return (kDTreeSet.containsKey(temp) &&
                    temp.distanceSquaredTo(point) < best.point.distanceSquaredTo(point));
        } else {
            yCopy = node.point.y();
            xCopy = point.x();
            temp = new Point(xCopy, yCopy);
            return (kDTreeSet.containsKey(temp) &&
                    temp.distanceSquaredTo(point) < best.point.distanceSquaredTo(point));
        }
    }
    */

    private static class KDNode {
        Point point;
        KDNode left;
        KDNode right;
        int depth;

        KDNode(Point point, int depth) {
            this.point = point;
            this.left = null;
            this.right = null;
            this.depth = depth;
        }

        int getDepth() {
            return this.depth;
        }
        KDNode getLeft() {
            return this.left;
        }
        KDNode getRight() {
            return this.right;
        }

        void setDepth(int depth) {
            this.depth = depth;
        }
    }
}
