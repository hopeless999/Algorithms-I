import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] thresholds;
    private final double confidence95;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.confidence95 = 1.96;
        this.trials = trials;
        this.thresholds = new double[this.trials];
        int i = 0;
        while (i < this.trials) {
            Percolation p = new Percolation(n);
            while (true) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
                if (p.percolates()) {
                    double opened = p.numberOfOpenSites();
                    this.thresholds[i] = opened / (n * n);
                    break;
                }
            }
            i++;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (this.trials == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(this.thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - ((this.confidence95 * stddev()) / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + ((this.confidence95 * stddev()) / Math.sqrt(this.trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("mean    = " + ps.mean());
        System.out.println("stddev  = " + ps.stddev());
        System.out
                .println("95% confidence interval = [" +
                                 ps.confidenceLo() + ", " +
                                 ps.confidenceHi() + "]");
    }

}
