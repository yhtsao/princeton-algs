import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private int numOfOpenSites = 0;
    private boolean[][] sites;
    private WeightedQuickUnionUF ufModel;
    private int topNodeIndex, bottomNodeIndex;

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        this.n = n;
        this.sites = new boolean[n][n];
        this.ufModel = new WeightedQuickUnionUF(n * n + 2);
        this.topNodeIndex = n * n;
        this.bottomNodeIndex = n * n + 1;
        connectTopAndBottomNode();
    }

    private void connectTopAndBottomNode() {
        // Connect first row to top node
        for (int i = 0; i < n; i++) {
            ufModel.union(topNodeIndex, i);
        }

        // Connect last row to bottom node
        for (int i = n * (n-1); i < n * n; i++) {
            ufModel.union(bottomNodeIndex, i);
        }
        // System.out.println(ufModel.count());
    }

    private int getSiteInUfModel(int row, int col) {
        return row * n + col;
    }

    /**
     *  open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        // System.out.println("Open site[" + row + ", " + col + "]");
        if (isOpen(row, col)) {
            return;
        }

        sites[row][col] = true;
        numOfOpenSites++;

         // Connect to adjacent open site
        int siteInUfModel = getSiteInUfModel(row, col);
       
        // Upper
        if (isValidValue(row-1, col) && isOpen(row - 1, col)) {
            int siteToConnect = getSiteInUfModel(row - 1, col);
            union(siteInUfModel, siteToConnect);
        }
        // Bottom
        if (isValidValue(row+1, col) && isOpen(row + 1, col)) {
            int siteToConnect = getSiteInUfModel(row + 1, col);
            union(siteInUfModel, siteToConnect);
        }
        // Left
        if (isValidValue(row, col-1) && isOpen(row, col - 1)) {
            int siteToConnect = getSiteInUfModel(row, col - 1);
            union(siteInUfModel, siteToConnect);
        }
        // Right
        if (isValidValue(row, col+1) && isOpen(row, col + 1)) {
            int siteToConnect = getSiteInUfModel(row, col + 1);
            union(siteInUfModel, siteToConnect);
        }
    } 

    private void union(int p, int q) {
        // System.out.println("Union {" + p + ", " + q + "}");
        ufModel.union(p, q);
        // System.out.println("Number of component in UF model = " + ufModel.count());
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValidValue(row, col)) {
            return false;
        }
        return sites[row][col];
    } 

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isValidValue(row - 1, col - 1) && numOfOpenSites == n * n) {
            return true;
        }
        return false;
    } 

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    } 

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return ufModel.connected(topNodeIndex, bottomNodeIndex);
    }

    private boolean isValidValue(int row, int col) {
        if (row >= n || row < 0 || col >= n || col < 0) {
            return false;
        }
        return true;
    }

    /**
     * test client (optional)
     */
    public static void main(String[] args) {
        int n = 5;
        Percolation model = new Percolation(n);
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(model.sites[i][j] + " ");
            }
        }
        System.out.println("");
    }
}