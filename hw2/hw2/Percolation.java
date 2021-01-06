package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    boolean[][] openMatrix;
    WeightedQuickUnionUF uf;
    boolean hasUpSite;
    int upSiteCol;
    int[][] next = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    int N;
    int openSiteNum;
    boolean isPercolated;

    public Percolation(int N) {
        this.N = N;
        openMatrix = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 1);
        hasUpSite = false;
        openSiteNum = 0;
        isPercolated = false;
    }

    public void open(int row, int col) {
        if ((row < 0) || (row >= N) || (col < 0) || (col >= N)) {
            throw new IllegalArgumentException("Illegal argument in open function");
        }
        if (openMatrix[row][col]) {
            return;
        }
        openSiteNum++;
        openMatrix[row][col] = true;
        for (int i = 0; i < 4; ++i) {
            int nextRow = row + next[i][0];
            int nextCol = col + next[i][1];
            if ((nextRow >= 0) && (nextRow < N) && (nextCol >= 0) && (nextCol < N)) {
                if (openMatrix[nextRow][nextCol]) {
                    uf.union(nextRow * N + nextCol, row * N + col);
                }
            }
        }
        if (row == 0) {
            uf.union(col, N * N);
        }
    }

    public boolean isOpen(int row, int col) {
        if ((row < 0) || (row >= N) || (col < 0) || (col >= N)) {
            throw new IllegalArgumentException("Illegal argument in isOpen function");
        }
        return openMatrix[row][col];
    }

    public boolean isFull(int row, int col) {
        if ((row < 0) || (row >= N) || (col < 0) || (col >= N)) {
            throw new IllegalArgumentException("Illegal argument in isFull function");
        }
        return uf.connected(row * N + col, N * N);
    }

    public int numberOfOpenSites() {
        return openSiteNum;
    }

    public boolean percolates() {
        for (int i = 0; i < N; ++i) {
            if (uf.connected((N - 1) * N + i, N * N)) {
                isPercolated = true;
                break;
            }
        }
        return isPercolated;
    }

    public static void main(String[] args) {
        PercolationFactory percolationFactory  = new PercolationFactory();
        PercolationStats percolationStats = new PercolationStats(40, 40, percolationFactory);
        System.out.println(percolationStats.mean());
    }
}
