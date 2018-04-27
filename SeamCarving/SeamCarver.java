import edu.princeton.cs.algs4.Picture;

import javax.jnlp.DownloadService;

public class SeamCarver {

    private class Pixel {
        // energy of this pixel
        double energy;

        // parent vertex in shortest path
        double parentX;
        double parentY;

        // shortest distance from source vertex
        double distTo = Double.POSITIVE_INFINITY;
    }

    private Picture picture;
    private int width, height;
    //private double[][] energy;
    private Pixel[][] pixels;

    /**
     * create a seam carver object based on the given picture
     *
     * @param picture
     */
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();

        computeEnergyOfPicture();
    }

    /**
     * current picture
     *
     * @return
     */
    public Picture picture() {
        return picture;
    }

    /**
     * width of current picture
     *
     * @return
     */
    public int width() {
        return this.width;
    }

    /**
     * height of current picture
     *
     * @return
     */
    public int height() {
        return this.height;
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
        pixels = new Pixel[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y].energy = computeEnergy(x, y);
            }
        }
    }

    private double computeEnergy(int x, int y) {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
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
        return null;
    }

    /**
     * sequence of indices for vertical seam
     *
     * @return
     */
    public int[] findVerticalSeam() {
        double minDist = Double.POSITIVE_INFINITY;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                relax(x, y);


            }
        }
        return null;
    }

    private void relax(int x, int y) {
        if (y + 1 > height) return;

        if (isUpdateDistTo(pixels[x][y], pixels[x][y + 1])) {
            pixels[x][y + 1].parentX = x;
            pixels[x][y + 1].parentY = y;
        }

        if (x - 1 >= 0 && isUpdateDistTo(pixels[x][y], pixels[x - 1][y + 1])) {
            pixels[x - 1][y + 1].parentX = x;
            pixels[x - 1][y + 1].parentY = y;
        }
        if (x + 1 < width && isUpdateDistTo(pixels[x][y], pixels[x + 1][y + 1])) {
            pixels[x + 1][y + 1].parentX = x;
            pixels[x + 1][y + 1].parentY = y;
        }

        //if (pixels[x][y + 1].distTo > pixels[x][y].distTo + pixels[x][y + 1].energy)
        //    pixels[x][y + 1].distTo = pixels[x][y].distTo + pixels[x][y + 1].energy;
        //
        //if (x - 1 >= 0 && pixels[x - 1][y + 1].distTo > pixels[x][y].distTo + pixels[x - 1][y + 1].energy)
        //    pixels[x - 1][y + 1].distTo = pixels[x][y].distTo + pixels[x - 1][y + 1].energy;
        //
        //if (x + 1 < width && pixels[x + 1][y + 1].distTo > pixels[x][y].distTo + pixels[x + 1][y + 1].energy)
        //    pixels[x + 1][y + 1].distTo = pixels[x][y].distTo + pixels[x + 1][y + 1].energy;

    }

    private boolean isUpdateDistTo(Pixel parent, Pixel child) {
        if (child.distTo > parent.distTo + child.energy) {
            child.distTo = parent.distTo + child.energy;
            return true;
        }
        return false;
    }

    /**
     * compute shortest path by topological order
     */
    private void findSeam() {

    }

    /**
     * remove horizontal seam from current picture
     *
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length == 0)
            throw new IllegalArgumentException();
        if (picture.height() <= 1) throw new IllegalArgumentException();
    }

    /**
     * remove vertical seam from current picture
     *
     * @param seam
     */
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length == 0)
            throw new IllegalArgumentException();
        if (picture.width() <= 1) throw new IllegalArgumentException();
    }
}