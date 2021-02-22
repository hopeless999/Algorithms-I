import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private byte[][] grid;
    private final int n;
    private final WeightedQuickUnionUF wqu;
    private final WeightedQuickUnionUF wqu2;
    private int number;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // Throw an IllegalArgumentException in the constructor if n ≤ 0
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;

        // Grid recording whether each site is opened or blocked
        this.grid = new byte[this.n][this.n];

        // The last 2 index will be virtual top sites and bottom sites
        this.wqu = new WeightedQuickUnionUF(this.n * this.n + 2);
        this.wqu2 = new WeightedQuickUnionUF(this.n * this.n + 1);
        this.number = 0;
    }

    private int map(int row, int col) {
        return (this.n * (row - 1) + col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // Throw exception if row or column out of range
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            // Set the state for this location as opened
            this.grid[row - 1][col - 1] = 1;
            this.number++;
            // Getting the number for this location
            int currNum = map(row, col);

            // If the site is not in top row and site above is opened
            if (row > 1 && isOpen(row - 1, col)) {
                this.wqu.union(currNum, map(row - 1, col));
                this.wqu2.union(currNum, map(row - 1, col));
            }

            // If the site is not in bottom row and the site below is opened
            if (row < this.n && isOpen(row + 1, col)) {
                this.wqu.union(currNum, map(row + 1, col));
                this.wqu2.union(currNum, map(row + 1, col));
            }

            // If the site is not in the leftmost column and the site on the left is opened
            if (col > 1 && isOpen(row, col - 1)) {
                this.wqu.union(currNum, map(row, col - 1));
                this.wqu2.union(currNum, map(row, col - 1));
            }

            // If the site is not in the rightmost column and the site on the right is opened
            if (col < this.n && isOpen(row, col + 1)) {
                this.wqu.union(currNum, map(row, col + 1));
                this.wqu2.union(currNum, map(row, col + 1));
            }

            // If the site is in virtual top row
            if (row == 1) {
                this.wqu.union(currNum, this.n * this.n);
                this.wqu2.union(currNum, this.n * this.n);
            }

            // If the site is in virtual bottom row
            if (row == this.n) {
                this.wqu.union(currNum, this.n * this.n + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // Throw exception if row or column out of range
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }

        if (this.grid[row - 1][col - 1] == 1) {
            return true;
        }

        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // Throw exception if row or column out of range
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }

        // If site is blocked then site won't be full
        if (!isOpen(row, col)) {
            return false;
        }

        // If the site is connected to any of the sites at top -> site is full
        if (this.wqu2.find(map(row, col)) == this.wqu2.find(this.n * this.n)) {
            return true;
        }

        // If site not connected to any sites at top
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.number;
    }

    // does the system percolate?
    public boolean percolates() {
        // Edge case when n = 1 and site is not opened
        if (this.n == 1 && !isOpen(1, 1)) {
            return false;
        }

        if (this.wqu.find(this.n * this.n + 1) == this.wqu.find(this.n * this.n)) {
            return true;
        }

        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 2);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        p.open(3, 1);

        System.out.println(p.percolates());
        WeightedQuickUnionUF w = new WeightedQuickUnionUF(9);
        w.union(3, 6);
        w.union(0, 3);
        System.out.println(w.find(6));
    }
}
