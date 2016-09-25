import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.ResizingArrayBag;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        segments = new LineSegment[0];
        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        this.points = points;
        for (int i = 0; i < points.length; i++) {
            boolean getLine = false;
            Point iPoint = points[i];

            for (int j = i + 1; j < points.length; j++) {
//                if (getLine) {
//                    break;
//                }
                Point jPoint = points[j];
                for (int k = j + 1; k < points.length; k++) {
//                    if (getLine) {
//                        break;
//                    }
                    Point kPoint = points[k];
                    //две точки всегда будут на одной прямой
//                    if (iPoint.slopeOrder().compare(jPoint, kPoint) == 0) {
                    if (iPoint.slopeTo(jPoint) == iPoint.slopeTo(kPoint)) {

                        Point lastPoint = null;
                        for (int l = k + 1; l < points.length; l++) {
                            Point lPoint = points[l];
                            if (iPoint.slopeTo(jPoint) == iPoint.slopeTo(lPoint)) {

                                Point higherPoint = maxPoint(maxPoint(iPoint, jPoint), maxPoint(kPoint, lPoint));
                                Point lowerPoint = minPoint(minPoint(iPoint, jPoint), minPoint(kPoint, lPoint));
//                                System.out.println("line of points: " + iPoint + " " + jPoint + " " + kPoint + " " + lPoint + " --- : " + higherPoint + " " + lowerPoint);
                                LineSegment ls = new LineSegment(lowerPoint, higherPoint);
                                getLine = true;
                                segments = addSegment(segments, ls);
//                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    private LineSegment[] addSegment(LineSegment[] seg, LineSegment ls) {
        LineSegment[] newSegments = new LineSegment[seg.length + 1];
        for (int i = 0; i < seg.length; i++) {
            newSegments[i] = seg[i];
        }
        newSegments[newSegments.length - 1] = ls;
        return newSegments;
    }

    private Point maxPoint(Point a, Point b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    private Point minPoint(Point a, Point b) {
        return a.compareTo(b) < 0 ? a : b;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("D:\\hog\\algs4partI-002\\homework2\\collinear-testing\\collinear\\input56.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
