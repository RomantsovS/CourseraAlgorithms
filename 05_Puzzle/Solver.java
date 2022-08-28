import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private class Node implements Comparable<Node> {
        public Board b;

        // Parent in the path
        private Node parent;

        private int moves;
        private int m;

        Node(Board b, int moves, Node parent) {
            this.b = b;
            this.moves = moves;
            this.parent = parent;
            m = b.manhattan();
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(moves + m, n.moves + n.m);
        }
    }

    private final boolean isSolvableVal;
    private final int minMoves;
    private final List<Board> path;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        edu.princeton.cs.algs4.MinPQ<Node> pq1 = new edu.princeton.cs.algs4.MinPQ<Node>();
        edu.princeton.cs.algs4.MinPQ<Node> pq2 = new edu.princeton.cs.algs4.MinPQ<Node>();

        pq1.insert(new Node(initial, 0, null));
        pq2.insert(new Node(initial.twin(), 0, null));

        boolean isSolvableLocal = false;
        int minMovesLocal = 0;
        List<Board> pathLocal = null;

        while (!pq1.isEmpty() || !pq2.isEmpty()) {
            Node n1 = pq1.delMin();
            Node n2 = pq2.delMin();
            if (n1.b.isGoal()) {
                minMovesLocal = n1.moves;
                isSolvableLocal = true;

                pathLocal = new ArrayList<Board>();
                while (true) {
                    pathLocal.add(n1.b);
                    if (n1.parent == null)
                        break;
                    n1 = n1.parent;
                }

                break;
            }
            if (n2.b.isGoal()) {
                break;
            }

            for (Board neighbor : n1.b.neighbors()) {
                if (n1.parent == null || !neighbor.equals(n1.parent.b)) {
                    Node newNode = new Node(neighbor, n1.moves + 1, n1);
                    pq1.insert(newNode);
                }
            }
            for (Board neighbor : n2.b.neighbors()) {
                if (n2.parent == null || !neighbor.equals(n2.parent.b)) {
                    Node newNode = new Node(neighbor, n2.moves + 1, n2);
                    pq2.insert(newNode);
                }
            }
        }

        isSolvableVal = isSolvableLocal;
        minMoves = minMovesLocal;
        path = pathLocal;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvableVal;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable())
            return minMoves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;

        Stack<Board> q = new Stack<Board>();

        for (Board b : path)
            q.push(b);

        return q;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        StdOut.println(initial);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
