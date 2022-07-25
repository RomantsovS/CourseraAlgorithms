import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] grid;
    private final int size;
    private int opensites = 0;
    private final WeightedQuickUnionUF uf;
    private int cnt = 0;
    private final int topsite;
    private final int botsite;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        size = n;
        grid = new boolean[size * size];

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                grid[i * size + j] = false;
            }
        }

        uf = new WeightedQuickUnionUF(size * size + 2);

        topsite = size * size;
        botsite = topsite + 1;

        if (cnt != 0)
            print();
    }

    private void print() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (grid[i * size + j])
                    System.out.print('1');
                else
                    System.out.print('0');
            }
            System.out.println();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size)
            throw new IllegalArgumentException();

        if (isOpen(row, col)) {
            // System.out.println("already opened: " + row + " " + col);
            return;
        }
        // System.out.println("open: " + row + " " + col);

        row = row - 1;
        col = col - 1;

        int p = row * size + col;
        int r = uf.find(p);
        cnt += 1;

        if (row > 0) {
            if (isOpen(row, col + 1) && r != uf.find((row - 1) * size + col)) {
                uf.union(p, (row - 1) * size + col);
                cnt += 2;
            }
        }
        if (row < size - 1) {
            if (isOpen(row + 2, col + 1) && r != uf.find((row + 1) * size + col)) {
                uf.union(p, (row + 1) * size + col);
                cnt += 2;
            }
        }
        if (col > 0) {
            if (isOpen(row + 1, col) && r != uf.find(row * size + col - 1)) {
                uf.union(p, row * size + col - 1);
                cnt += 2;
            }
        }
        if (col < size - 1) {
            if (isOpen(row + 1, col + 2) && r != uf.find(row * size + col + 1)) {
                uf.union(p, row * size + col + 1);
                cnt += 2;
            }
        }

        if (row == 0 && r != uf.find(topsite)) {
            uf.union(p, topsite);
            // System.out.println("connected to virt top: " + row + " " + col
            //                            + " " + uf.find(p));

        }
        if (row == size - 1 && r != uf.find(botsite)) {
            uf.union(p, botsite);
            // System.out.println("connected to virt bot: " + row + " " + col
            //                            + " " + uf.find(p));
        }

        grid[p] = true;

        opensites = opensites + 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size)
            throw new IllegalArgumentException();
        row = row - 1;
        col = col - 1;
        return grid[row * size + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size)
            throw new IllegalArgumentException();

        if (!isOpen(row, col))
            return false;

        if (row == 1)
            return true;

        row = row - 1;
        col = col - 1;

        return uf.find(row * size + col) == uf.find(topsite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensites;
    }

    // does the system percolate?
    public boolean percolates() {
        // for (int i = 0; i < size; ++i) {
        //     if (isFull(size, i + 1))
        //         return true;
        // }
        // return false;
        return uf.find(topsite) == uf.find(botsite);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Percolation p = new Percolation(4);
        //
        // System.out.println("is open 1 1: " + p.isOpen(1, 1));
        // System.out.println("is full 1 1: " + p.isFull(1, 1));
        //
        // p.print();
        // System.out.println("percolates: " + p.percolates());
        // p.open(1, 1);
        // p.print();
        // System.out.println("percolates: " + p.percolates());
        // p.open(1, 2);
        // p.print();
        // System.out.println("percolates: " + p.percolates());
        // p.open(2, 1);
        // p.print();
        // System.out.println("percolates: " + p.percolates());
        // p.open(3, 1);
        // p.print();
        // System.out.println("percolates: " + p.percolates());
        // p.open(4, 1);
        // p.print();
        // System.out.println("percolates: " + p.percolates());
        // p.open(4, 2);
        // p.print();
        // System.out.println("percolates: " + p.percolates());
        //
        // System.out.println("is full 1 1: " + p.isFull(1, 1));
        // System.out.println("is full 2 1: " + p.isFull(2, 1));
        // System.out.println("is full 2 2: " + p.isFull(2, 2));
        // System.out.println("is full 3 3: " + p.isFull(3, 3));
        // System.out.println("is full 4 2: " + p.isFull(4, 2));

        int n = 4;
        Percolation p = new Percolation(n);

        // for (int i = 1; i <= n; ++i) {
        //     for (int j = 1; j <= n; ++j) {
        //         System.out.println("i j: " + i + " " + j);
        //         p.open(i, j);
        //         System.out.println("cnt= " + p.cnt);
        //         p.isOpen(i, j);
        //         p.percolates();
        //         System.out.println("cnt= " + p.cnt);
        //         System.out.println(p.numberOfOpenSites());
        //         p.isFull(i, j);
        //         System.out.println("cnt= " + p.cnt);
        //     }
        // }
        while (!p.percolates()) {
            System.out.println("cnt= " + p.cnt);
            p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            System.out.println("open cnt= " + p.cnt);
            PercolationVisualizer.draw(p, n);
        }
        System.out.println("cnt= " + p.cnt);
    }
}
