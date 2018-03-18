import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private Point[] points;
    private int numOfLine;
    private List<LineSegment> lineSegments;
    private List<Point> existEndPoints;

    /**
     * finds all line segments containing 4 points
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
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
        this.existEndPoints = new ArrayList<>();

        // sort point array in ascending order
        Arrays.sort(this.points);

        // find collinear points
        findLineSegmentWith4Points();

        this.existEndPoints = null;
    }

    private void findLineSegmentWith4Points() {
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            findLineSegmentWith3Points(p, i + 1, points.length);
        }
    }

    private void findLineSegmentWith3Points(Point p, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            Point q = points[i];
            if (p.compareTo(q) == 0)
                throw new IllegalArgumentException();
            double slope = p.slopeTo(q);
            findThirdPointOfGivenSlope(p, slope, i + 1, hi);

        }
    }

    private void findThirdPointOfGivenSlope(Point p, double slope, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            Point q = points[i];
            if (p.slopeTo(q) == slope) {
                findFourthPointsOfGivenSlope(p, slope, i + 1, hi);
            }
        }
    }

    private void findFourthPointsOfGivenSlope(Point p, double slope, int lo, int hi) {
        for (int i = hi - 1; i >= lo; i--) {
            Point q = points[i];
            if (p.slopeTo(q) == slope && !existEndPoints.contains(q)) {
                this.existEndPoints.add(q);
                updateLineSegment(p, q);
                break;
            }
        }
    }

    private void updateLineSegment(Point p, Point q) {
        this.lineSegments.add(new LineSegment(p, q));
        numOfLine++;
    }

    /**
     * the number of line segments
     *
     * @return
     */
    public int numberOfSegments() {
        return this.numOfLine;
    }

    /**
     * the line segments
     *
     * @return
     */
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
}
