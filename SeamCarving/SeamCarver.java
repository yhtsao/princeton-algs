import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private class Pixel {
        // energy of this pixel
        double energy;

        // parent vertex in shortest path
        int edgeTo;

        // shortest distance from source vertex
        double distTo = Double.POSITIVE_INFINITY;
    }

    private Picture picture;
    //private double[][] energy;
    private Pixel[][] pixels;
    private Pixel[][] transposedPixels;

    /**
     * create a seam carver object based on the given picture
     *
     * @param picture
     */
    public SeamCarver(Picture picture) {
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
        if (x < 0 || x >= picture.width() || y < 0 || y >= picture.height())
            throw new IllegalArgumentException();
        return pixels[x][y].energy;
    }

    private void computeEnergyOfPicture() {
        pixels = new Pixel[width()][height()];
        transposedPixels = new Pixel[height()][width()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                pixels[x][y] = new Pixel();
                pixels[x][y].energy = computeEnergy(x, y);

                transposedPixels[y][x] = new Pixel();
                transposedPixels[y][x].energy = pixels[x][y].energy;
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
        for (int y = 0; y < width() - 1; y++) {
            for (int x = 0; x < height(); x++) {
                relax(transposedPixels, x, y);
            }
        }

        int minX = 0;
        double minDist = Double.POSITIVE_INFINITY;
        for (int x = 0; x < height(); x++) {
            if (transposedPixels[x][width() - 1].distTo < minDist) {
                minDist = transposedPixels[x][width() - 1].distTo;
                minX = x;
            }
        }

        int[] seam = new int[width()];
        for (int i = width() - 1; i >= 0; i--) {
            seam[i] = minX;
            minX = transposedPixels[minX][i].edgeTo;
        }
        return seam;
    }

    /**
     * sequence of indices for vertical seam
     *
     * @return
     */
    public int[] findVerticalSeam() {
        for (int y = 0; y < height() - 1; y++) {
            for (int x = 0; x < width(); x++) {
                relax(pixels, x, y);
            }
        }

        int minX = 0;
        double minDist = Double.POSITIVE_INFINITY;
        for (int x = 0; x < width(); x++) {
            if (pixels[x][height() - 1].distTo < minDist) {
                minDist = pixels[x][height() - 1].distTo;
                minX = x;
            }
        }

        int[] seam = new int[height()];
        for (int i = height() - 1; i >= 0; i--) {
            seam[i] = minX;
            minX = pixels[minX][i].edgeTo;
        }
        return seam;
    }

    private void relax(Pixel[][] array, int x, int y) {
        if (y + 1 > array[0].length) return;

        if (y == 0) {
            array[x][y].distTo = array[x][y].energy;
        }

        if (isUpdateDistTo(array[x][y], array[x][y + 1])) {
            array[x][y + 1].edgeTo = x;
        }

        if (x - 1 >= 0 && isUpdateDistTo(array[x][y], array[x - 1][y + 1])) {
            array[x - 1][y + 1].edgeTo = x;
        }
        if (x + 1 < array.length && isUpdateDistTo(array[x][y], array[x + 1][y + 1])) {
            array[x + 1][y + 1].edgeTo = x;
        }
    }

    private boolean isUpdateDistTo(Pixel parent, Pixel child) {
        if (child.distTo > parent.distTo + child.energy) {
            child.distTo = parent.distTo + child.energy;
            return true;
        }
        return false;
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
