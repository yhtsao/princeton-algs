import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class PointTest {

    @Test
    public void normalSlope() {
        Point p = new Point(100, 100);
        Point q = new Point(200, 200);
        double slope = q.slopeTo(p);
        Assertions.assertEquals(1, slope);
    }

    @Test
    public void verticalSlope() {
        Point p = new Point(100, 100);
        Point q = new Point(100, 200); // up
        double slope = q.slopeTo(p);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, slope);

        q = new Point(100, 0); // down
        slope = q.slopeTo(p);
        Assertions.assertEquals(Double.POSITIVE_INFINITY, slope);
    }

    @Test
    public void horizontalSlope() {
        Point p = new Point(100, 100);
        Point q = new Point(200, 100); // right
        double slope = q.slopeTo(p);
        Assertions.assertEquals(0.0, slope);

        q = new Point(0, 100); // left
        slope = q.slopeTo(p);
        Assertions.assertEquals(0.0, slope);
    }

    @Test
    public void samePointSlope() {
        Point p = new Point(100, 100);
        Point q = new Point(100, 100);
        double slope = q.slopeTo(p);
        Assertions.assertEquals(Double.NEGATIVE_INFINITY, slope);
    }

    @Test
    public void compareTo() {
        Point p = new Point(100, 100);

        /**
         * p == q
         */
        Point q = new Point(100, 100);
        Assertions.assertEquals(0, p.compareTo(q));

        /**
         * p < q
         */
        q = new Point(100, 200);
        Assertions.assertEquals(-1, p.compareTo(q));
        q = new Point(200, 100);
        Assertions.assertEquals(-1, p.compareTo(q));

        /**
         * p > q
         */
        q = new Point(100, 0);
        Assertions.assertEquals(1, p.compareTo(q));
        q = new Point(0, 100);
        Assertions.assertEquals(1, p.compareTo(q));
    }

    @Test
    public void slopeOrder() {
        In in = new In("testcases/PointSlopeCompare01.txt");
        int n = in.readInt();
        Point p = new Point(in.readInt(), in.readInt());
        Point[] points = new Point[n];
        int i = 0;
        while (!in.isEmpty()) {
            points[i++] = new Point(in.readInt(), in.readInt());
        }

        Point[] correctOrderPoints = Arrays.copyOf(points, n);
        StdRandom.shuffle(points);
        for (i = 0; i < n; i++)
            System.out.println(points[i].toString() + ", " + p.slopeTo(points[i]));

        Arrays.sort(points, p.slopeOrder());

        for (i = 0; i < n; i++) {
            Assertions.assertEquals(correctOrderPoints[n - i - 1], points[i]);
        }
    }
}