import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class Board {

    private final int[][] mTiles;
    private int emptyIndex;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        mTiles = new int[tiles.length][tiles.length];

        for (int r = 0; r < tiles.length; ++r) {
            for (int c = 0; c < tiles.length; ++c) {
                mTiles[r][c] = tiles[r][c];
                if (mTiles[r][c] == 0)
                    emptyIndex = r * mTiles.length + c;
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(mTiles.length);
        str.append('\n');

        for (int r = 0; r < mTiles.length; ++r) {
            for (int c = 0; c < mTiles.length; ++c) {
                if (c > 0)
                    str.append(' ');
                str.append(mTiles[r][c]);
            }
            str.append('\n');
        }

        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return mTiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        for (int i = 0; i < mTiles.length * mTiles.length - 1; ++i) {
            if (mTiles[i / mTiles.length][i % mTiles.length] != i + 1)
                ++res;
        }

        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < mTiles.length * mTiles.length; ++i) {
            int r = i / mTiles.length;
            int c = i % mTiles.length;
            int val = mTiles[r][c];
            if (val != 0) {
                int valR = (val - 1) / mTiles.length;
                int valC = (val - 1) % mTiles.length;
                res += Math.abs(r - valR) + Math.abs(c - valC);
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this) {
            return true;
        }

        if (getClass() != y.getClass())
            return false;

        // typecast o to Complex so that we can compare data members
        Board b = (Board) y;

        if (dimension() != b.dimension())
            return false;

        for (int r = 0; r < mTiles.length; ++r) {
            for (int c = 0; c < mTiles.length; ++c) {
                if (mTiles[r][c] != b.mTiles[r][c])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();
        int[][] tiles = new int[dimension()][dimension()];
        for (int r = 0; r < dimension(); ++r)
            for (int c = 0; c < dimension(); ++c)
                tiles[r][c] = mTiles[r][c];

        int r = emptyIndex / mTiles.length;
        int c = emptyIndex % mTiles.length;

        if (c > 0) {
            swap(tiles, r, c - 1, r, c);
            q.enqueue(new Board(tiles));
            swap(tiles, r, c - 1, r, c);
        }
        if (c + 1 < dimension()) {
            swap(tiles, r, c + 1, r, c);
            q.enqueue(new Board(tiles));
            swap(tiles, r, c + 1, r, c);
        }
        if (r > 0) {
            swap(tiles, r - 1, c, r, c);
            q.enqueue(new Board(tiles));
            swap(tiles, r - 1, c, r, c);
        }
        if (r + 1 < dimension()) {
            swap(tiles, r + 1, c, r, c);
            q.enqueue(new Board(tiles));
            swap(tiles, r + 1, c, r, c);
        }

        return q;
    }

    private void swap(int[][] array, int a, int b, int c, int d) {
        int temp = array[a][b];
        array[a][b] = array[c][d];
        array[c][d] = temp;
    }

    // // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] tiles = new int[dimension()][dimension()];
        int rSwap = -1, cSwap = 0, rr = -1, cc = 0;
        for (int r = 0; r < dimension(); ++r)
            for (int c = 0; c < dimension(); ++c) {
                if (mTiles[r][c] != 0) {
                    if (rSwap == -1) {
                        rSwap = r;
                        cSwap = c;
                    }
                    else if (rr == -1) {
                        rr = r;
                        cc = c;
                    }
                }
                tiles[r][c] = mTiles[r][c];
            }

        swap(tiles, rSwap, cSwap, rr, cc);
        return new Board(tiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board b = new Board(tiles);
        System.out.println(b.toString());
        System.out.println("dimension: " + b.dimension());
        System.out.println("hamming: " + b.hamming());
        System.out.println("manhattan: " + b.manhattan());

        System.out.println("twin:\n" + b.twin());

        tiles[0][0] = 2;
        tiles[0][1] = 1;

        Board b2 = new Board(tiles);
        System.out.println(b.equals(b2));

        for (Board ne : b.neighbors())
            System.out.println(ne.toString());
    }

}
