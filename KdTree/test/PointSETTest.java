import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class PointSETTest {
    @Test
    public void input10() {
        List<Point2D> point2DList = readPointFromFile("testcases/input10.txt");
        PointSET brute = new PointSET();
        Assertions.assertTrue(brute.isEmpty());

        for (Point2D point : point2DList) {
            brute.insert(point);
        }
        Assertions.assertFalse(brute.isEmpty());
        Assertions.assertEquals(point2DList.size(), brute.size());

        for (Point2D point : point2DList) {
            Assertions.assertTrue(brute.contains(point));
        }
        Point2D point = new Point2D(0.111, 0.111);
        Assertions.assertFalse(brute.contains(point));

        // nearest test
        System.out.println("Nearest point to " + point.toString());
        System.out.println(brute.nearest(point));

        // rectangle text
        RectHV rect = new RectHV(0.1, 0.1, 0.5, 0.5);
        System.out.println("In Rect " + rect.toString());
        Iterator<Point2D> iter = brute.range(rect).iterator();
        while (iter.hasNext())
            System.out.println(iter.next());

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