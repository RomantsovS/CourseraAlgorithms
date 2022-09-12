import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {
    private static class Node implements Comparable<Node> {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }

        @Override
        public int compareTo(Node n) {
            return p.compareTo(n.p);
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        size = 0;
    }

    private void insertR(Node n, Point2D p, int dimension) {
        if (dimension == 0) {
            if (p.x() < n.p.x()) {
                if (n.lb == null)
                    n.lb = new Node(p,
                                    new RectHV(n.rect.xmin(), n.rect.ymin(), n.p.x(),
                                               n.rect.ymax()));
                else
                    insertR(n.lb, p, (dimension + 1) % 2);
            }
            else {
                if (n.rt == null)
                    n.rt = new Node(p,
                                    new RectHV(n.p.x(), n.rect.ymin(), n.rect.xmax(),
                                               n.rect.ymax()));
                else
                    insertR(n.rt, p, (dimension + 1) % 2);
            }
        }
        else {
            if (p.y() < n.p.y()) {
                if (n.lb == null)
                    n.lb = new Node(p,
                                    new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(),
                                               n.p.y()));
                else
                    insertR(n.lb, p, (dimension + 1) % 2);
            }
            else {
                if (n.rt == null)
                    n.rt = new Node(p,
                                    new RectHV(n.rect.xmin(), n.p.y(), n.rect.xmax(),
                                               n.rect.ymax()));
                else
                    insertR(n.rt, p, (dimension + 1) % 2);
            }
        }
    }

    private boolean containsR(Node n, Point2D p, int dimension) {
        if (n == null)
            return false;

        if (n.p.equals(p))
            return true;

        if (dimension == 0) {
            if (p.x() < n.p.x()) {
                return containsR(n.lb, p, (dimension + 1) % 2);
            }
            else {
                return containsR(n.rt, p, (dimension + 1) % 2);
            }
        }
        else {
            if (p.y() < n.p.y()) {
                return containsR(n.lb, p, (dimension + 1) % 2);
            }
            else {
                return containsR(n.rt, p, (dimension + 1) % 2);
            }
        }
    }

    private void drawR(Node n, int dimension) {
        if (n == null)
            return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.05);

        StdDraw.point(n.p.x(), n.p.y());

        StdDraw.setPenRadius(0.01);

        if (dimension == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }
        drawR(n.lb, (dimension + 1) % 2);
        drawR(n.rt, (dimension + 1) % 2);
    }

    private void rangeR(Node n, RectHV rect, ArrayList<Point2D> res) {
        if (n == null)
            return;

        if (!rect.intersects(n.rect))
            return;

        if (rect.contains(n.p))
            res.add(n.p);

        rangeR(n.lb, rect, res);
        rangeR(n.rt, rect, res);

    }

    private Point2D nearestR(Node n, Point2D p, Point2D top, double minDist, int dimension) {
        if (n == null)
            return null;

        double md = p.distanceSquaredTo(n.p);
        if (md < minDist) {
            top = n.p;
            minDist = md;
        }

        double lenLeft = Double.POSITIVE_INFINITY;
        double lenRight = Double.POSITIVE_INFINITY;

        if (n.lb != null) {
            lenLeft = n.lb.rect.distanceSquaredTo(p);
        }
        if (n.rt != null) {
            lenRight = n.rt.rect.distanceSquaredTo(p);
        }

        if (dimension == 0) {
            if (p.x() < n.p.x()) {
                if (lenLeft < minDist) {
                    top = nearestR(n.lb, p, top, minDist, (dimension + 1) % 2);
                    minDist = p.distanceSquaredTo(top);
                }
                if (lenRight < minDist) {
                    top = nearestR(n.rt, p, top, minDist, (dimension + 1) % 2);
                    minDist = p.distanceSquaredTo(top);
                }
            }
            else {
                if (lenRight < minDist) {
                    top = nearestR(n.rt, p, top, minDist, (dimension + 1) % 2);
                    minDist = p.distanceSquaredTo(top);
                }
                if (lenLeft < minDist) {
                    top = nearestR(n.lb, p, top, minDist, (dimension + 1) % 2);
                    minDist = p.distanceSquaredTo(top);
                }
            }
        }
        else {
            if (p.y() < n.p.y()) {
                if (lenLeft < minDist) {
                    top = nearestR(n.lb, p, top, minDist, (dimension + 1) % 2);
                    minDist = p.distanceSquaredTo(top);
                }
                if (lenRight < minDist) {
                    top = nearestR(n.rt, p, top, minDist, (dimension + 1) % 2);
                    minDist = p.distanceSquaredTo(top);
                }
            }
            else {
                if (lenRight < minDist) {
                    top = nearestR(n.rt, p, top, minDist, (dimension + 1) % 2);
                    minDist = p.distanceSquaredTo(top);
                }
                if (lenLeft < minDist) {
                    top = nearestR(n.lb, p, top, minDist, (dimension + 1) % 2);
                    minDist = p.distanceSquaredTo(top);
                }
            }
        }

        return top;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty()) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            ++size;
            return;
        }
        if (contains(p))
            return;

        insertR(root, p, 0);
        ++size;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return containsR(root, p, 0);
    }

    // draw all points to standard draw
    public void draw() {
        drawR(root, 0);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        ArrayList<Point2D> res = new ArrayList<>();

        rangeR(root, rect, res);

        return res;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty())
            return null;

        Point2D top = new Point2D(-1, -1);
        top = nearestR(root, p, top, Double.POSITIVE_INFINITY, 0);
        return top;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pointSet = new KdTree();

        In in = new In(args[0]);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();

            Point2D p = new Point2D(x, y);
            pointSet.insert(p);
        }

        pointSet.draw();

        for (Point2D p : pointSet.range(new RectHV(0.52, 0.01, 0.75, 0.23)))
            StdOut.println("range res: " + p.toString());
    }
}
