import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    public int n = 0;
    public int trials = 0;

    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials; 
    }  

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return 0;

    }  
    
    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return 0;
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return 0;
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return 0;
    }
 
    public static void main(String[] args) {  // test client (described below)
        System.out.println("Hello");

        PercolationStats computeModel = initPercolationStatModel(args);
        Percolation model = new Percolation(computeModel.n);
        System.out.println(model.n);
        
        while (!model.percolates()) {
            int row = StdRandom.uniform(computeModel.n);
            int col = StdRandom.uniform(computeModel.n);
            model.open(row, col);
        }
        System.out.println("Number of open sites = " + model.numberOfOpenSites());

    }     
    
    private static PercolationStats initPercolationStatModel(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        int n = Integer.parseInt(args[0]);
        int trail = Integer.parseInt(args[1]);
        if (n <= 0 || trail <= 0) {
            throw new IllegalArgumentException();
        }
        PercolationStats computeModel = new PercolationStats(n, trail);
        return computeModel;
    }
}