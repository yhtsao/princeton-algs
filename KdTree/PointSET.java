import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

public class PointSET {

    private SET<Point2D> set;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        this.set = new SET<>();
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return set.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> point2DStack = new Stack<>();
        for (Point2D p : set) {
            if (rect.contains(p))
                point2DStack.push(p);
        }
        return point2DStack;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *6
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Iterator<Point2D> iter = set.iterator();
        double min = 100;
        Point2D nearestPoint = null;
        while (iter.hasNext()) {
            Point2D point = iter.next();
            double d = p.distanceSquaredTo(point);
            if (d < min) {
                min = d;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
}
