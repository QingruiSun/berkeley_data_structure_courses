package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {

    private int N;
    private int T;
    private double[] simulationResult;
    private double meanValue;
    private double stdDeviationValue;
    private double confidenceLowValue;
    private double confidenceHighValue;

    /* Perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        this.T = T;
        simulationResult = new double[T];
        for (int i = 0; i < T; ++i) {
            Percolation p = pf.make(N);
            simulationResult[i] = simulation(p);
        }
        calulateMean();
        calculateStdDeviationValue();
        calculateConfidenceLow();
        calculateConfidenceHigh();
    }

    /* Simulate a Monte Carlo Sampling in a grid. */
    private double simulation(Percolation p) {
        boolean percolatedFlag = false;
        int openNum = 0;
        while (!percolatedFlag) {
            int randRow = StdRandom.uniform(N);
            int randCol = StdRandom.uniform(N);
            if (!p.isOpen(randRow, randCol)) {
                p.open(randRow, randCol);
            }
            percolatedFlag = p.percolates();
            if (percolatedFlag) {
                openNum = p.numberOfOpenSites();
                System.out.println("openNum:" + openNum);
                break;
            }
        }
        return openNum * 1.0 / (N * 1.0) / (N * 1.0);
    }

    private void calulateMean() {
        double totalValue = 0;
        for (int i = 0; i < T; ++i) {
            totalValue = totalValue + simulationResult[i];
        }
        meanValue = totalValue / T;
    }

    private void calculateStdDeviationValue() {
        if (T == 1) {
            stdDeviationValue = Double.NaN;
            return;
        }
        double squareSum = 0;
        for (int i = 0; i < T; ++i) {
            squareSum = squareSum + (simulationResult[i] - meanValue)
                    * (simulationResult[i] - meanValue);
        }
        double deviationValue = squareSum / (T - 1);
        stdDeviationValue = Math.sqrt(deviationValue);
    }

    public double mean() {
        return meanValue;
    }

    public double stddev() {
        return stdDeviationValue;
    }

    private void calculateConfidenceLow() {
        confidenceLowValue = meanValue - 1.96 * stdDeviationValue / Math.sqrt(1.0 * T);
    }

    private void calculateConfidenceHigh() {
        confidenceHighValue = meanValue + 1.96 * stdDeviationValue / Math.sqrt(1.0 * T);
    }

    public double confidenceLow() {
        return confidenceLowValue;
    }

    public double confidenceHigh() {
        return confidenceHighValue;
    }

}
