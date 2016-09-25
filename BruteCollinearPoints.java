import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.ResizingArrayBag;

import java.util.ArrayList;

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
        ArrayList<Point> collinear;
        for (int i = 0; i < points.length; i++) {
            Point iPoint = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point jPoint = points[j];
                collinear = new ArrayList<Point>();
                collinear.add(iPoint);
                collinear.add(jPoint);
                for (int k = j + 1; k < points.length; k++) {
                    Point kPoint = points[k];
                    if (iPoint.slopeTo(jPoint) == iPoint.slopeTo(kPoint)) {
                        collinear.add(kPoint);
                        for (int l = k + 1; l < points.length; l++) {
                            Point lPoint = points[l];
                            if (iPoint.slopeTo(jPoint) == iPoint.slopeTo(lPoint)) {
                                collinear.add(lPoint);

                            }
                        }
                        if (collinear.size() >= 4) {
                            Point higherPoint = collinear.get(0);
                            Point lowerPoint = collinear.get(0);
                            for (Point toComp : collinear) {
                                if (toComp.compareTo(higherPoint) > 0) {
                                    higherPoint = toComp;
                                }
                                if (toComp.compareTo(lowerPoint) < 0) {
                                    lowerPoint = toComp;
                                }
                            }
                            segments = addSegment(segments, new LineSegment(lowerPoint, higherPoint));
                            break;
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
