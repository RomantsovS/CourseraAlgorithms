import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> list;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }

        Point[] mPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(mPoints);
        Point lastPoint = mPoints[0];

        for (int i = 1; i < mPoints.length; ++i) {
            if (mPoints[i].compareTo(lastPoint) == 0)
                throw new IllegalArgumentException();
            lastPoint = mPoints[i];
        }

        list = new ArrayList<LineSegment>();

        for (int i = 0; i < mPoints.length; ++i) {
            Arrays.sort(mPoints);
            Point p = mPoints[i];

            Arrays.sort(mPoints, p.slopeOrder());

            int j = 0;
            double lastSlope = p.slopeTo(mPoints[j]);
            int cnt = 1;
            int startIndex = j;
            int lastIndex = j;

            for (++j; j < mPoints.length; ++j) {
                double curSlope = p.slopeTo(mPoints[j]);
                if (curSlope != lastSlope) {
                    if (cnt >= 4) {
                        Arrays.sort(mPoints, startIndex, lastIndex);
                        if (p.compareTo(mPoints[startIndex]) < 0)
                            list.add(new LineSegment(p, mPoints[lastIndex]));
                    }
                    lastSlope = curSlope;
                    cnt = 2;
                    startIndex = j;
                }
                else {
                    lastIndex = j;
                    ++cnt;
                }
            }

            if (cnt >= 4) {
                Arrays.sort(mPoints, startIndex, lastIndex);
                if (p.compareTo(mPoints[startIndex]) < 0)
                    list.add(new LineSegment(p, mPoints[lastIndex]));
            }
        }
    }

    public int numberOfSegments() {
        return list.size();
    }

    public LineSegment[] segments() {
        return list.toArray(new LineSegment[list.size()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
