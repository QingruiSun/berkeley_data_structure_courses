import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture picture;
    private int width;
    private int height;
    private double[][][] pixelArray;
    private double[][] energyArray;


    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
        buildPixelArray();
        calculateEnergy();
    }

    private void buildPixelArray() {
        if (picture == null) {
            return;
        }
        pixelArray = new double[height][width][3];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                /* Use this way to get RGB, forbidden use function getRGB() */
                Color c = picture.get(i, j);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                pixelArray[j][i][0] = r;
                pixelArray[j][i][1] = g;
                pixelArray[j][i][2] = b;
            }
        }
    }

    public Picture picture() {
        /* Use copy constructor.  Defensive copy is important! */
        return new Picture(picture);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    private void calculateEnergy() {
        energyArray = new double[height][width];
        double[][][] rowEnergyArray = new double[height][width][3];
        double[][][] colEnergyArray = new double[height][width][3];
        for (int i = 1; i < height - 1; ++i) {
            for (int j = 0; j < width; ++j) {
                for (int k = 0; k < 3; ++k) {
                    rowEnergyArray[i][j][k] = pixelArray[i + 1][j][k] - pixelArray[i - 1][j][k];
                }
            }
        }
        /* Calculate the first row. */
        int preRow = height - 1;
        int nextRow = 1 % height;
        for (int j = 0; j < width; ++j) {
            for (int k = 0; k < 3; ++k) {
                rowEnergyArray[0][j][k] = pixelArray[nextRow][j][k] - pixelArray[preRow][j][k];
            }
        }
        /* Calculate the last row.*/
        preRow = (height - 2) % height;
        nextRow = 0;
        for (int j = 0; j < width; ++j) {
            for (int k = 0; k < 3; ++k) {
                rowEnergyArray[height - 1][j][k] = pixelArray[nextRow][j][k]
                        - pixelArray[preRow][j][k];
            }
        }
        for (int i = 1; i < width - 1; ++i) {
            for (int j = 0; j < height; ++j) {
                for (int k = 0; k < 3; ++k) {
                    colEnergyArray[j][i][k] = pixelArray[j][i + 1][k] - pixelArray[j][i - 1][k];
                }
            }
        }
        /* Calculate the first col. */
        int preCol = width - 1;
        int nextCol = 1 % width;
        for (int j = 0; j < height; ++j) {
            for (int k = 0; k < 3; ++k) {
                colEnergyArray[j][0][k] = pixelArray[j][nextCol][k] - pixelArray[j][preCol][k];
            }
        }
        nextCol = 0;
        preCol = (width - 2) % width;
        for (int j = 0; j < height; ++j) {
            for (int k = 0; k < 3; ++k) {
                colEnergyArray[j][width - 1][k] = pixelArray[j][nextCol][k]
                        - pixelArray[j][preCol][k];
            }
        }
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                for (int k = 0; k < 3; ++k) {
                    energyArray[i][j] = energyArray[i][j] + colEnergyArray[i][j][k]
                            * colEnergyArray[i][j][k]
                            + rowEnergyArray[i][j][k] * rowEnergyArray[i][j][k];
                }
            }
        }
    }

    /* Energy of pixel at column x and row y.*/
    public double energy(int x, int y) {
        if ((x < 0) || (x >= width) || (y < 0) || (y >= height)) {
            throw new IndexOutOfBoundsException("The arguments for energy function out of bounds.");
        }
        return energyArray[y][x];
    }

    public int[] findHorizontalSeam() {
        double[][] newEnergyArray = new double[width][height];
        int preWidth = width;
        int preHeight = height;
        double[][] preEnergyArray = energyArray;
        /* Transpose the array. */
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                newEnergyArray[i][j] = energyArray[j][i];
            }
        }
        width = preHeight;
        height = preWidth;
        energyArray = newEnergyArray;
        int[] horizontalSeam = findVerticalSeam();
        width = preWidth;
        height = preHeight;
        energyArray = preEnergyArray;
        return horizontalSeam;
    }

    public int[] findVerticalSeam() {
        /* dpArray was used to implement dynamic programming algorithm. */
        double[][] dpArray = new double[height][width];
        /* path array was used to record path of dynamic programming algorithm. */
        int[][] path = new int[height][width];
        for (int i = 0; i < width; ++i) {
            dpArray[0][i] = energyArray[0][i];
            path[0][i] = -1;
        }
        for (int i = 1; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int minIndex = j;
                double minCost = dpArray[i - 1][j];
                if (j - 1 >= 0) {
                    if (dpArray[i - 1][j - 1] < minCost) {
                        minCost = dpArray[i - 1][j - 1];
                        minIndex = j - 1;
                    }
                }
                if (j + 1 < width) {
                    if (dpArray[i - 1][j + 1] < minCost) {
                        minCost = dpArray[i - 1][j + 1];
                        minIndex = j + 1;
                    }
                }
                path[i][j] = minIndex;
                dpArray[i][j] = minCost + energyArray[i][j];
            }
        }
        double minTotalCost = Double.MAX_VALUE;
        int minTotalIndex = -1;
        for (int i = 0; i < width; ++i) {
            if (dpArray[height - 1][i] < minTotalCost) {
                minTotalCost = dpArray[height - 1][i];
                minTotalIndex = i;
            }
        }
        int[] verticalSeam = new int[height];
        int ptrIndex = minTotalIndex;
        for (int i = height - 1; i >= 0; --i) {
            verticalSeam[i] = ptrIndex;
            ptrIndex = path[i][ptrIndex];
        }
        return verticalSeam;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width) {
            throw new IllegalArgumentException("Seam length doesn't match the picture.");
        }
        for (int i = 1; i < seam.length; ++i) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("Index in seam is illegal.");
            }
        }
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
        height = picture.height();
        width = picture.width();
        buildPixelArray();
        calculateEnergy();
    }


    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height) {
            throw new IllegalArgumentException("Seam length doesn't match the picture.");
        }
        for (int i = 1; i < seam.length; ++i) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException("Index in seam is illegal.");
            }
        }
        picture = SeamRemover.removeVerticalSeam(picture, seam);
        height = picture.height();
        width = picture.width();
        buildPixelArray();
        calculateEnergy();
    }
}
