import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private int n = 0;
    private int trials = 0;
    private double[] openSiteFractionArr;
    private double mean = 0, stddev = 0;
    
    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials; 
        this.openSiteFractionArr = new double[trials];
        runModelAndGetStatResult();
    }  

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        this.mean = StdStats.mean(openSiteFractionArr);
        return mean;

    }  
    
    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        this.stddev = StdStats.stddev(openSiteFractionArr);
        return stddev;
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return this.mean - CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return this.mean + CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }
 
    public static void main(String[] args) { 

        initPercolationStatModel(args);
    }     
    
    private static void initPercolationStatModel(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        int n = Integer.parseInt(args[0]);
        int trail = Integer.parseInt(args[1]);
        if (n <= 0 || trail <= 0) {
            throw new IllegalArgumentException();
        }
        PercolationStats computeModel = new PercolationStats(n, trail);
        computeModel.mean();
    }

    private void runModelAndGetStatResult() {
        double numberOfSites = this.n * this.n;
        for (int i = 0; i < this.trials; i++) {
            int openSite = runPercolationModel();
            double openSiteFraction = (double) openSite / numberOfSites;
            this.openSiteFractionArr[i] = openSiteFraction;
        }
        System.out.println("mean = " + mean());
        System.out.println("stddev = " + stddev());
        System.out.println("95% confidence interval = [" + confidenceHi() + ", " + confidenceLo() + "]");
    }

    private int runPercolationModel() {
        Percolation model = new Percolation(this.n);
        
        while (!model.percolates()) {
            int row = StdRandom.uniform(this.n);
            int col = StdRandom.uniform(this.n);
            model.open(row, col);
        }
        // System.out.println("Number of open sites = " + model.numberOfOpenSites());
        return model.numberOfOpenSites();
    }

}