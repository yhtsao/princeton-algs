import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;

    /**
     * create a seam carver object based on the given picture
     *
     * @param picture
     */
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException();

        this.picture = new Picture(picture);
        computeEnergyOfPicture();
    }

    /**
     * current picture
     *
     * @return
     */
    public Picture picture() {
        return new Picture(picture);
    }

    /**
     * width of current picture
     *
     * @return
     */
    public int width() {
        return picture.width();
    }

    /**
     * height of current picture
     *
     * @return
     */
    public int height() {
        return picture.height();
    }

    /**
     * energy of pixel at column x and row y
     *
     * @param x
     * @param y
     * @return
     */
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new IllegalArgumentException();
        return energy[x][y];
    }

    private void computeEnergyOfPicture() {
        energy = new double[width()][height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                energy[x][y] = computeEnergy(x, y);
            }
        }
    }

    private double computeEnergy(int x, int y) {
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        } else {
            double xGradient = getXGradient(x, y);
            double yGradient = getYGradient(x, y);
            return Math.sqrt(xGradient + yGradient);
        }
    }

    private double getXGradient(int x, int y) {
        int rgbX1 = picture.getRGB(x - 1, y);
        int rgbX2 = picture.getRGB(x + 1, y);
        double xGradient = computeRedDiff(rgbX1, rgbX2)
                + computeGreenDiff(rgbX1, rgbX2) + computeBlueDiff(rgbX1, rgbX2);
        return xGradient;
    }

    private double getYGradient(int x, int y) {
        int rgbX1 = picture.getRGB(x, y - 1);
        int rgbX2 = picture.getRGB(x, y + 1);
        double xGradient = computeRedDiff(rgbX1, rgbX2)
                + computeGreenDiff(rgbX1, rgbX2) + computeBlueDiff(rgbX1, rgbX2);
        return xGradient;
    }

    private double computeRedDiff(int rgbX1, int rgbX2) {
        int rX1 = (rgbX1 >> 16) & 0xFF;
        int rX2 = (rgbX2 >> 16) & 0xFF;
        return Math.pow(rX1 - rX2, 2);
    }

    private double computeGreenDiff(int rgbX1, int rgbX2) {
        int rX1 = (rgbX1 >> 8) & 0xFF;
        int rX2 = (rgbX2 >> 8) & 0xFF;
        return Math.pow(rX1 - rX2, 2);
    }

    private double computeBlueDiff(int rgbX1, int rgbX2) {
        int rX1 = (rgbX1 >> 0) & 0xFF;
        int rX2 = (rgbX2 >> 0) & 0xFF;
        return Math.pow(rX1 - rX2, 2);
    }

    /**
     * sequence of indices for horizontal seam
     *
     * @return
     */
    public int[] findHorizontalSeam() {
        /* transpose the energy array */
        double[][] transposedEnergy = new double[height()][width()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++)
                transposedEnergy[y][x] = energy[x][y];
        }
        return findSeam(transposedEnergy, height(), width());
    }

    public int[] findVerticalSeam() {
        return findSeam(energy, width(), height());
    }

    private int[] findSeam(double[][] energy, int width, int height) {
         /* initialize distTo and edgeTo array */
        int[][] edgeTo = new int[width][height];
        double[][] distTo = new double[width][height];
        for (int x = 0; x < width; x++)
            Arrays.fill(distTo[x], Double.POSITIVE_INFINITY);

        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                relax(energy, edgeTo, distTo, x, y);
            }
        }
        /* get seam from distTo and edgeTo */
        int[] seam = getSeam(edgeTo, distTo, width, height);

        return seam;
    }

    private void relax(double[][] energy, int[][] edgeTo, double[][] distTo, int x, int y) {
        if (y + 1 > energy[0].length) return;

        if (y == 0) {
            distTo[x][y] = energy[x][y];
        }

        if (distTo[x][y + 1] > energy[x][y + 1] + distTo[x][y]) {
            edgeTo[x][y + 1] = x;
            distTo[x][y + 1] = energy[x][y + 1] + distTo[x][y];
        }

        if (x - 1 >= 0 && distTo[x - 1][y + 1] > energy[x - 1][y + 1] + distTo[x][y]) {
            edgeTo[x - 1][y + 1] = x;
            distTo[x - 1][y + 1] = energy[x - 1][y + 1] + distTo[x][y];
        }

        if (x + 1 < energy.length && distTo[x + 1][y + 1] > energy[x + 1][y + 1] + distTo[x][y]) {
            edgeTo[x + 1][y + 1] = x;
            distTo[x + 1][y + 1] = energy[x + 1][y + 1] + distTo[x][y];
        }
    }

    private int[] getSeam(int[][] edgeTo, double[][] distTo, int width, int height) {
        /* find minimum distance vertex */
        int minX = 0;
        double minDist = Double.POSITIVE_INFINITY;
        for (int x = 0; x < width; x++) {
            if (distTo[x][height - 1] < minDist) {
                minDist = distTo[x][height - 1];
                minX = x;
            }
        }

        /* get path from vertex with minimum distance to top */
        int[] seam = new int[height];
        for (int i = height - 1; i >= 0; i--) {
            seam[i] = minX;
            minX = edgeTo[minX][i];
        }
        return seam;
    }

    /**
     * remove horizontal seam from current picture
     *
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {
        if (!isValidSeam(seam, false))
            throw new IllegalArgumentException();

        int x = 0;
        Picture newPicture = new Picture(width(), height() - 1);
        for (int removedPixel : seam) {
            int newY = 0;
            for (int y = 0; y < height(); y++) {
                if (y == removedPixel) continue;
                Color color = picture.get(x, y);
                newPicture.set(x, newY++, color);
            }
            x++;
        }
        this.picture = newPicture;
        computeEnergyOfPicture();
    }

    /**
     * remove vertical seam from current picture
     *
     * @param seam
     */
    public void removeVerticalSeam(int[] seam) {
        if (!isValidSeam(seam, true))
            throw new IllegalArgumentException();

        int y = 0;
        Picture newPicture = new Picture(width() - 1, height());
        for (int removedPixel : seam) {
            int newX = 0;
            for (int x = 0; x < width(); x++) {
                if (x == removedPixel) continue;
                Color color = picture.get(x, y);
                newPicture.set(newX++, y, color);
            }
            y++;
        }
        this.picture = newPicture;
        computeEnergyOfPicture();
    }

    private boolean isValidSeam(int[] seam, boolean isVertical) {
        if (picture == null || seam == null || seam.length == 0)
            return false;
        if (isVertical && (picture.width() <= 1 || seam.length != height()))
            return false;
        else if (!isVertical && (picture.height() <= 1 || seam.length != width()))
            return false;

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1)
                return false;
        }
        return true;
    }
}
