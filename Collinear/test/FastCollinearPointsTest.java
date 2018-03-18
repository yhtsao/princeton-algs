import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FastCollinearPointsTest {
    @Test
    public void lineWith6point() {
        Point[] points = readPointFromFile("testcases/input6.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        Assertions.assertEquals(1, collinearPoints.numberOfSegments());

        Point p = new Point(14000, 10000);
        Point q = new Point(32000, 10000);
        LineSegment lineSegment = new LineSegment(p, q);
        Assertions.assertEquals(lineSegment, collinearPoints.segments()[0]);
    }

    @Test
    public void lineWith8point() {
        Point[] points = readPointFromFile("testcases/input8.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        Assertions.assertEquals(2, collinearPoints.numberOfSegments());

        Point p = new Point(3000, 4000);
        Point q = new Point(20000, 21000);
        LineSegment line1 = new LineSegment(p, q);

        p = new Point(10000, 0);
        q = new Point(0, 10000);
        LineSegment line2 = new LineSegment(p, q);

        LineSegment[] lines = collinearPoints.segments();

        Assertions.assertTrue(Arrays.asList(lines).contains(line1));
        Assertions.assertTrue(Arrays.asList(lines).contains(line2));
    }

    @Test
    public void lineWith40Point() {
        Point[] points = readPointFromFile("testcases/input40.txt");
        List<String> ans = readAnsFromFile("testcases/input40ans.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        Assertions.assertEquals(ans.size(), collinearPoints.numberOfSegments());

        LineSegment[] lines = collinearPoints.segments();
        for (LineSegment line : lines) {
            Assertions.assertTrue(ans.contains(line.toString()));
        }
    }

    @Test
    public void lineWith5Horizontal() {
        Point[] points = readPointFromFile("testcases/horizontal5.txt");
        List<String> ans = readAnsFromFile("testcases/horizontal5ans.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        Assertions.assertEquals(ans.size(), collinearPoints.numberOfSegments());

        LineSegment[] lines = collinearPoints.segments();
        for (LineSegment line : lines) {
            Assertions.assertTrue(ans.contains(line.toString()));
        }
    }

    @Test
    public void lineWith5Vertical() {
        Point[] points = readPointFromFile("testcases/vertical5.txt");
        List<String> ans = readAnsFromFile("testcases/vertical5ans.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        Assertions.assertEquals(ans.size(), collinearPoints.numberOfSegments());


        LineSegment[] lines = collinearPoints.segments();
        for (LineSegment line : lines) {
            //System.out.println(line.toString());
            Assertions.assertTrue(ans.contains(line.toString()));
        }
    }

    @Test
    public void lineWith100Horizontal() {
        Point[] points = readPointFromFile("testcases/horizontal100.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        Assertions.assertEquals(100, collinearPoints.numberOfSegments());
    }

    @Test
    public void lineWith100Vertical() {
        Point[] points = readPointFromFile("testcases/vertical100.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        Assertions.assertEquals(100, collinearPoints.numberOfSegments());
    }

    @Test
    public void lineWith10000Points() {
        Point[] points = readPointFromFile("testcases/input10000.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        System.out.println("number of line = " + collinearPoints.numberOfSegments());
        for (int i = 0; i < collinearPoints.segments().length; i++) {
            System.out.println(collinearPoints.segments()[i]);
        }
    }

    @Test
    public void lineWithEquidistant() {
        Point[] points = readPointFromFile("testcases/equidistant.txt");
        List<String> ans = readAnsFromFile("testcases/equidistantans.txt");

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        Assertions.assertEquals(ans.size(), collinearPoints.numberOfSegments());

        LineSegment[] lines = collinearPoints.segments();
        for (LineSegment line : lines) {
            //System.out.println(line.toString());
            Assertions.assertTrue(ans.contains(line.toString()));
        }
    }

    @Test
    public void duplicate() {
        Point[] points = readPointFromFile("testcases/duplicate5.txt");

        boolean catchIllegalArguExcep = false;
        try {
            new FastCollinearPoints(points);
        } catch (IllegalArgumentException e) {
            catchIllegalArguExcep = true;
        }

        Assertions.assertTrue(catchIllegalArguExcep);
    }

    @Test
    public void nullPoint() {
        Point[] points = new Point[5];
        for (int i = 0; i < 5; i++) {
            points[i] = null;
        }
        boolean catchIllegalArguExcep = false;
        try {
            new FastCollinearPoints(points);
        } catch (IllegalArgumentException e) {
            catchIllegalArguExcep = true;
        }
        Assertions.assertTrue(catchIllegalArguExcep);
    }

    private Point[] readPointFromFile(String filename) {
        // read the n points from a file
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        return points;
    }

    private List<String> readAnsFromFile(String filename) {
        In in = new In(filename);
        List<String> ans = new ArrayList<>();
        while (!in.isEmpty()) {
            String str = in.readLine();
            ans.add(str);
        }
        return ans;
    }
}