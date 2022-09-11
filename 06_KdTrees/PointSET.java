import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.setPenColor(StdDraw.BLACK);
        double penRadius = 0.01;
        StdDraw.setPenRadius(penRadius);

        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        ArrayList<Point2D> res = new ArrayList<>();

        for (Point2D p : set) {
            if (rect.contains(p))
                res.add(p);
        }

        return res;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        Point2D top = null;
        double minLen = Double.POSITIVE_INFINITY;

        for (Point2D pt : set) {
            if (top == null)
                top = pt;
            double len = p.distanceSquaredTo(pt);
            if (len < minLen) {
                minLen = len;
                top = pt;
            }
        }
        return top;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET pointSet = new PointSET();

        In in = new In(args[0]);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();

            Point2D p = new Point2D(x, y);
            pointSet.insert(p);
        }

        pointSet.draw();
    }
}
