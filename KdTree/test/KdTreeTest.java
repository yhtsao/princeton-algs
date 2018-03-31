import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

class KdTreeTest {
    @Test
    public void input10() {
        List<Point2D> point2DList = readPointFromFile("testcases/input10.txt");
        KdTree kdTree = new KdTree();
        Assertions.assertTrue(kdTree.isEmpty());

        for (Point2D point : point2DList) {
            kdTree.insert(point);
        }
        Assertions.assertFalse(kdTree.isEmpty());
        Assertions.assertEquals(point2DList.size(), kdTree.size());
        for (Point2D point : point2DList) {
            Assertions.assertTrue(kdTree.contains(point));
        }

        // construct a brute solution for testing
        PointSET brute = constructPointSet(point2DList);

        // test nearest
        testNearest(brute, kdTree);

        // test range
        testRangeContains(brute, kdTree);
    }

    private void testNearest(PointSET brute, KdTree kdTree) {
        Point2D randomPoint = new Point2D(StdRandom.uniform(0, 1), StdRandom.uniform(0, 1));
        Assertions.assertEquals(brute.contains(randomPoint), kdTree.contains(randomPoint));
    }

    private void testRangeContains(PointSET brute, KdTree kdTree) {
        RectHV rectHV = new RectHV(StdRandom.uniform(0, 0.5), StdRandom.uniform(0, 0.5),
                StdRandom.uniform(0.5, 1), StdRandom.uniform(0.5, 1));
        Iterator<Point2D> iterBrute = brute.range(rectHV).iterator();
        TreeSet<Point2D> set = new TreeSet<>();
        while (iterBrute.hasNext())
            set.add(iterBrute.next());

        Iterator<Point2D> iterKdTree = kdTree.range(rectHV).iterator();
        while (iterKdTree.hasNext())
            Assertions.assertTrue(set.contains(iterKdTree.next()));

    }

    private PointSET constructPointSet(List<Point2D> point2DList) {
        PointSET brute = new PointSET();
        for (Point2D point : point2DList) {
            brute.insert(point);
        }
        return brute;
    }

    private List<Point2D> readPointFromFile(String filename) {
        // initialize the two data structures with point from file
        In in = new In(filename);
        List<Point2D> point2DList = new ArrayList<>();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            point2DList.add(p);

        }
        return point2DList;
    }
}