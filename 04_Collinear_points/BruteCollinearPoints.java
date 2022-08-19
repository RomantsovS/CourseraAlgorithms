import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> list;

    public BruteCollinearPoints(Point[] points) {
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
            for (int j = i + 1; j < mPoints.length; ++j) {
                double slope1 = mPoints[i].slopeTo(mPoints[j]);
                for (int k = j + 1; k < mPoints.length; ++k) {
                    double slope2 = mPoints[i].slopeTo(mPoints[k]);
                    if (slope1 == slope2) {
                        for (int kk = k + 1; kk < mPoints.length; ++kk) {
                            double slope3 = mPoints[i].slopeTo(mPoints[kk]);
                            if (slope1 == slope3) {
                                list.add(new LineSegment(mPoints[i], mPoints[kk]));
                            }
                        }
                    }
                }
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
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        for (LineSegment ls : bcp.segments()) {
            System.out.println(ls.toString());
        }
    }
}
