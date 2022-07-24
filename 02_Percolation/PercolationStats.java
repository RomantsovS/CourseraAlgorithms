import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double MNOJ = 1.96;
    private final double[] treshholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        treshholds = new double[trials];

        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
                // p.Print();
                // System.out.println("percolates: " + p.percolates());
            }

            treshholds[i] = 1.0 * p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(treshholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(treshholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double d = MNOJ * stddev() / Math.sqrt(treshholds.length);
        return mean() - d;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double d = MNOJ * stddev() / Math.sqrt(treshholds.length);
        return mean() + d;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());


        System.out.println("95% confidence interval = ["
                                   + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
