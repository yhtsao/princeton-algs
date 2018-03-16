import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        int n = 10;
        Point p = new Point(0, 0);
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point((i + 1) * 10, (i + 1) * 10);
        }
        StdRandom.shuffle(points);
        for (int i = 0; i < n; i++) {
            System.out.println(points[i].toString());
        }

        Arrays.sort(points, p.slopeOrder());

        for (int i = n - 1; i >= 0; i--) {
            Point q = new Point((i + 1) * 10, (i + 1) * 10);
            Assertions.assertEquals(q, points[i]);
        }
    }
}