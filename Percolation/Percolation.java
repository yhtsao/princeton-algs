import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;

public class Percolation {

    private int n;
    private int numOfOpenSites = 0;
    private boolean[][] sites;
    private WeightedQuickUnionUF ufModel;
    private WeightedQuickUnionUF ufModelForFullTest;
    private int topNodeIndex, bottomNodeIndex;

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        this.n = n;
        this.sites = new boolean[n + 1][n + 1];
        this.ufModel = new WeightedQuickUnionUF(n * n + 2);
        this.ufModelForFullTest = new WeightedQuickUnionUF(n * n + 1);
        this.topNodeIndex = 0;
        this.bottomNodeIndex = n * n + 1;
        connectTopAndBottomNode();
    }

    public static void main(String[] args) {
        In in = new In("percolation-test/input2.txt");      // input file
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            System.out.println("isFull: " + perc.isFull(i, j));
            System.out.println("percolate: " + perc.percolates());
        }
    }

    private void connectTopAndBottomNode() {
        // Connect first row to top node
        for (int i = 1; i <= n; i++) {
            ufModel.union(topNodeIndex, i);
            ufModelForFullTest.union(topNodeIndex, i);
        }

        // Connect last row to bottom node
        for (int i = n * (n - 1) + 1; i <= n * n; i++) {
            ufModel.union(bottomNodeIndex, i);
        }
    }

    private int getSiteInUfModel(int row, int col) {
        return (row - 1) * n + col;
    }

    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        sites[row][col] = true;
        numOfOpenSites++;

        // Connect to adjacent open site
        int siteInUfModel = getSiteInUfModel(row, col);

        // Upper
        connectAdjacentNode(siteInUfModel, row - 1, col);

        // Bottom
        connectAdjacentNode(siteInUfModel, row + 1, col);

        // Left
        connectAdjacentNode(siteInUfModel, row, col - 1);

        // Right
        connectAdjacentNode(siteInUfModel, row, col + 1);
    }

    private void connectAdjacentNode(int siteInUfModel, int adjacetNodeRow, int adjacentNodeCol) {
        if (isValidValue(adjacetNodeRow, adjacentNodeCol) && isOpen(adjacetNodeRow, adjacentNodeCol)) {
            int siteToConnect = getSiteInUfModel(adjacetNodeRow, adjacentNodeCol);
            union(siteInUfModel, siteToConnect);
        }
    }

    private void union(int p, int q) {
        ufModel.union(p, q);
        ufModelForFullTest.union(p, q);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValidValue(row, col)) {
            throw new IllegalArgumentException();
        }
        return sites[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValidValue(row, col)) {
            throw new IllegalArgumentException();
        }
        if (!isOpen(row, col)) {
            return false;
        }
        int siteToCheck = getSiteInUfModel(row, col);
        return ufModelForFullTest.connected(topNodeIndex, siteToCheck);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        if (n == 1) {
            return isOpen(1, 1);
        }
        return ufModel.connected(topNodeIndex, bottomNodeIndex);
    }

    private boolean isValidValue(int row, int col) {
        if (row > n || row <= 0 || col > n || col <= 0) {
            return false;
        }
        return true;
    }
}