import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        g = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfsV =
                new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsW =
                new BreadthFirstDirectedPaths(g, w);

        return length(bfsV, bfsW);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);

        int res = Integer.MAX_VALUE;
        int anc = -1;

        for (int vert = 0; vert < g.V(); vert++) {
            if (bfsV.hasPathTo(vert) && bfsW.hasPathTo(vert)) {
                if (bfsV.distTo(vert) + bfsW.distTo(vert) < res) {
                    res = bfsV.distTo(vert) + bfsW.distTo(vert);
                    anc = vert;
                }
            }
        }

        return res == Integer.MAX_VALUE ? -1 : anc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        try {
            BreadthFirstDirectedPaths bfsV =
                    new BreadthFirstDirectedPaths(g, v);
            BreadthFirstDirectedPaths bfsW =
                    new BreadthFirstDirectedPaths(g, w);

            return length(bfsV, bfsW);
        }
        catch (IllegalArgumentException e) {
            if ("zero vertices".equals(e.getMessage())) {
                return -1;
            }
            throw e;
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        try {
            BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
            BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);

            int res = Integer.MAX_VALUE;
            int anc = -1;

            for (int vert = 0; vert < g.V(); vert++) {
                if (bfsV.hasPathTo(vert) && bfsW.hasPathTo(vert)) {
                    if (bfsV.distTo(vert) + bfsW.distTo(vert) < res) {
                        res = bfsV.distTo(vert) + bfsW.distTo(vert);
                        anc = vert;
                    }
                }
            }

            return res == Integer.MAX_VALUE ? -1 : anc;
        }
        catch (IllegalArgumentException e) {
            if (e.getMessage().equals("zero vertices")) return -1;
            throw e;
        }
    }

    private int length(BreadthFirstDirectedPaths bfsV,
                       BreadthFirstDirectedPaths bfsW) {

        int res = Integer.MAX_VALUE;

        for (int vert = 0; vert < g.V(); vert++) {
            if (bfsV.hasPathTo(vert) && bfsW.hasPathTo(vert)) {
                int dist = bfsV.distTo(vert) + bfsW.distTo(vert);
                res = Math.min(res, dist);
            }
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
