import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p) {
            this.p = p;
            this.rect = null;
            this.lb = null;
            this.rt = null;
        }
    }

    private Node root;
    private int numOfPoint;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        this.numOfPoint = 0;
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return (numOfPoint == 0);
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return numOfPoint;
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            root = new Node(p);
            RectHV rect = new RectHV(0, 0, 1, 1);
            root.rect = rect;
            numOfPoint++;
            return;
        }
        searchVertical(root, p, true);
    }
    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return false;
        Node endNode = searchVertical(root, p, false);
        return endNode.p.equals(p);
    }

    private Node searchVertical(Node node, Point2D p, boolean isInsert) {
        if (node.p.equals(p)) return node;

        if (p.x() >= node.p.x() && node.rt != null) { // goto right subtree
            return searchHorizontal(node.rt, p, isInsert);
        } else if (p.x() < node.p.x() && node.lb != null) { // goto left subtree
            return searchHorizontal(node.lb, p, isInsert);
        }

        if (isInsert) {

            addNode(node, p, true, p.x() >= node.p.x());
        }
        return node;

    }

    private Node searchHorizontal(Node node, Point2D p, boolean isInsert) {
        if (node.p.equals(p)) return node;

        if (p.y() >= node.p.y() && node.rt != null) { // goto right subtree
            return searchVertical(node.rt, p, isInsert);
        } else if (p.y() < node.p.y() && node.lb != null) { // goto left subtree
            return searchVertical(node.lb, p, isInsert);
        }

        if (isInsert) {
            addNode(node, p, false, p.y() >= node.p.y());
        }
        return node;
    }

    private void addNode(Node parent, Point2D p, boolean isVerticalSplit, boolean isRightSubtree) {
        Node child = new Node(p);

        RectHV rectHV;
        if (isVerticalSplit) {
            if (!isRightSubtree) {
                rectHV = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                        parent.p.x(), parent.rect.ymax());
            } else {
                rectHV = new RectHV(parent.p.x(), parent.rect.ymin(),
                        parent.rect.xmax(), parent.rect.ymax());
            }
        } else {
            if (!isRightSubtree) {
                rectHV = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                        parent.rect.xmax(), parent.p.y());
            } else {
                rectHV = new RectHV(parent.rect.xmin(), parent.p.y(),
                        parent.rect.xmax(), parent.rect.ymax());
            }
        }
        child.rect = rectHV;

        if (isRightSubtree)
            parent.rt = child;
        else
            parent.lb = child;

        numOfPoint++;
    }
    /**
     * draw all points to standard draw
     */
    public void draw() {
        drawTree(root, true);
    }

    private void drawTree(Node node, boolean isVertical) {
        if (node == null) return;

        drawTree(node.lb, !isVertical);
        drawTree(node.rt, !isVertical);

        StdDraw.point(node.p.x(), node.p.y());
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
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
        Stack<Point2D> stack = new Stack<>();
        findRange(root, rect, stack);
        return stack;
    }

    private void findRange(Node node, RectHV rectHV, Stack<Point2D> stack) {
        if (node == null)
            return;

        if (!node.rect.intersects(rectHV))
            return;

        if (rectHV.contains(node.p)) {
            stack.push(node.p);
        }

        findRange(node.lb, rectHV, stack);
        findRange(node.rt, rectHV, stack);
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node node, Point2D p, Point2D minP, boolean isVertical) {
        if (node == null) return minP;

        if (node.p.distanceSquaredTo(p) < minP.distanceSquaredTo(p))
            minP = node.p;

        if (isVertical) {
            if (p.x() < node.p.x()) {
                minP = nearest(node.lb, p, minP, !isVertical);

                if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < minP.distanceSquaredTo(p)) {
                    minP = nearest(node.rt, p, minP, !isVertical);
                }
            } else {
                minP = nearest(node.rt, p, minP, !isVertical);

                if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < minP.distanceSquaredTo(p)) {
                    minP = nearest(node.lb, p, minP, !isVertical);
                }
            }
        } else {
            if (p.y() < node.p.y()) {
                minP = nearest(node.lb, p, minP, !isVertical);

                if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < minP.distanceSquaredTo(p)) {
                    minP = nearest(node.rt, p, minP, !isVertical);
                }
            } else {
                minP = nearest(node.rt, p, minP, !isVertical);

                if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < minP.distanceSquaredTo(p)) {
                    minP = nearest(node.lb, p, minP, !isVertical);
                }
            }
        }

        return minP;
    }
}
