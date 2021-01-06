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
        uf = new WeightedQuickUnionUF(N * N);
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
        if (row == 0) {
            if (!hasUpSite) {
                hasUpSite = true;
                upSiteCol = col;
            }
        }
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
            uf.union(col, upSiteCol);
        }
        if (row == N - 1) {
            if (uf.connected(row * N + col, upSiteCol)) {
                isPercolated = true;
            }
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
        return uf.connected(row * N + col, upSiteCol);
    }

    public int numberOfOpenSites() {
        return openSiteNum;
    }

    public boolean percolates() {
        return isPercolated;
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(30, 100, pf);
        System.out.println(ps.mean());

    }
}
