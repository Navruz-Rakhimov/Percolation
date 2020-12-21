import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private Percolation percolation;
    private double[] x;
    private int trial;
    private double mean;

    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0){
            throw new IllegalArgumentException("Illegal Argument");
        }
        x = new double[trials];
        trial = trials;

        for(int i = 0; i < trial; i++) {

            percolation = new Percolation(n);

            while(!percolation.percolates()){
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                percolation.open(row, col);
            }
            x[i] = (double)percolation.numberOfOpenSites()/(n*n);
        }
    }

    public double mean() {
        return StdStats.mean(x);
    }

    public double stddev() {
        return StdStats.stddev(x);
    }

    public double confidenceLo() {
        return mean() - (1.96*stddev())/Math.sqrt(trial);
    }

    public double confidenceHi() {
        return mean() + (1.96*stddev())/Math.sqrt(trial);
    }

    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(1000, 100);
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.stddev());
        System.out.println("[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

}
