import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FastCollinearPoints {
    private Point[] points;
    private int numOfLine;
    private List<LineSegment> lineSegments;
    private List<MyLine> existLines;

    private class MyLine {
        private Point endPoint;
        private double slope;

        public MyLine(Point endPoint, double slope) {
            this.endPoint = endPoint;
            this.slope = slope;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof MyLine)) return false;
            MyLine myLine = (MyLine) obj;
            return Double.compare(myLine.slope, slope) == 0 &&
                    Objects.equals(endPoint, myLine.endPoint);
        }
    }

    /**
     * finds all line segments containing 4 or more points
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            this.points[i] = points[i];
        }
        this.numOfLine = 0;
        this.lineSegments = new ArrayList<>();
        this.existLines = new ArrayList<>();

        // sort point array in ascending order
        Arrays.sort(this.points);

        // find collinear points
        findCollinearPoints();

        this.existLines = null;
    }

    private void findCollinearPoints() {
        for (int i = 0; i < points.length - 1; i++) { // n
            Point p = points[i];
            if (p == null || p.compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
            sortPointsBySlopeToP(p, i + 1, points.length);
        }
    }

    private void sortPointsBySlopeToP(Point p, int lo, int hi) {
        Point[] pointsForSorting = Arrays.copyOfRange(points, lo, hi);

        Arrays.sort(pointsForSorting, p.slopeOrder()); // nlgn

        int slopeIndex = 0;
        while (slopeIndex < pointsForSorting.length - 2) { // n
            double currentSlope = p.slopeTo(pointsForSorting[slopeIndex]);
            slopeIndex = checkIfColliearPoints(p, pointsForSorting, slopeIndex, currentSlope);
        }
    }

    private int checkIfColliearPoints(Point p, Point[] pointsForSorting, int slopeIndex, double currentSlope) {
        Point q = null;
        // check if the fourth or more points that have same slope to p
        if (currentSlope != p.slopeTo(pointsForSorting[slopeIndex + 2])) {
            return slopeIndex + 1;
        }
        for (int j = slopeIndex + 2; j < pointsForSorting.length; j++) {
            if (currentSlope != p.slopeTo(pointsForSorting[j])) {
                break;
            }
            slopeIndex = j + 1;
            q = pointsForSorting[j];
        }
        updateLineSegment(p, q);
        return slopeIndex;
    }

    private void updateLineSegment(Point p, Point q) {
        if (p == null || q == null)
            return;

        MyLine myLine = new MyLine(q, p.slopeTo(q));
        if (existLines.contains(myLine)) {
            return;
        }
        existLines.add(myLine);
        lineSegments.add(new LineSegment(p, q));
        numOfLine++;

    }

    /**
     * the number of line segments
     *
     * @return
     */
    public int numberOfSegments() {
        return numOfLine;
    }

    /**
     * the line segments
     */
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[numOfLine]);
    }
}
