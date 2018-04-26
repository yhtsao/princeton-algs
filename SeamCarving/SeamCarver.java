import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;

    /**
     * create a seam carver object based on the given picture
     *
     * @param picture
     */
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.energy = new double[picture.width()][picture.height()];
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
        return this.picture.width();
    }

    /**
     * height of current picture
     *
     * @return
     */
    public int height() {
        return this.picture.height();
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

        if (energy[x][y] != 0) return energy[x][y];

        if (x == 0 || x == picture.width() - 1
                || y == 0 || y == picture.height() - 1) {
            energy[x][y] = 1000;
        } else {
            double xGradient = getXGradient(x, y);
            double yGradient = getYGradient(x, y);
            energy[x][y] = Math.sqrt(xGradient + yGradient);
        }

        return energy[x][y];
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
        return null;
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