import edu.princeton.cs.algs4.Picture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

class SeamCarverTest {
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    @Test
    public void seamTest3x4() {
        Picture picture = new Picture("SeamCarving/testcases/3x4.png");
        SeamCarver carver = new SeamCarver(picture);

        int[] verticalSeam = carver.findVerticalSeam();
        int[] verticalSeamAns = new int[]{0, 1, 1, 0};
        for (int i = 0; i < verticalSeam.length; i++)
            Assertions.assertEquals(verticalSeamAns[i], verticalSeam[i]);
        Assertions.assertEquals(2456.6156, getEnergyOfSeam(carver, verticalSeam, VERTICAL));

        int[] horizontalSeam = carver.findHorizontalSeam();
        int[] horizontalSeamAns = new int[]{1, 2, 1};
        for (int i = 0; i < horizontalSeam.length; i++)
            Assertions.assertEquals(horizontalSeamAns[i], horizontalSeam[i]);

        Assertions.assertEquals(2228.0877, getEnergyOfSeam(carver, horizontalSeam, HORIZONTAL));
    }

    @Test
    public void seamTest5x6() {
        Picture picture = new Picture("SeamCarving/testcases/5x6.png");
        SeamCarver carver = new SeamCarver(picture);

        int[] verticalSeam = carver.findVerticalSeam();
        int[] verticalSeamAns = new int[]{1, 2, 2, 3, 2, 1};
        for (int i = 0; i < verticalSeam.length; i++)
            Assertions.assertEquals(verticalSeamAns[i], verticalSeam[i]);
        Assertions.assertEquals(2769.5289, getEnergyOfSeam(carver, verticalSeam, VERTICAL));

        int[] horizontalSeam = carver.findHorizontalSeam();
        int[] horizontalSeamAns = new int[]{2, 3, 2, 3, 2};
        for (int i = 0; i < horizontalSeam.length; i++)
            Assertions.assertEquals(horizontalSeamAns[i], horizontalSeam[i]);

        Assertions.assertEquals(2583.1989, getEnergyOfSeam(carver, horizontalSeam, HORIZONTAL));

        carver.removeVerticalSeam(verticalSeam);
        System.out.println(carver.picture().toString());
    }

    @Test
    public void seamTest6x5() {
        Picture picture = new Picture("SeamCarving/testcases/6x5.png");
        SeamCarver carver = new SeamCarver(picture);

        int[] verticalSeam = carver.findVerticalSeam();
        int[] verticalSeamAns = new int[]{3, 4, 3, 2, 1};
        for (int i = 0; i < verticalSeam.length; i++)
            Assertions.assertEquals(verticalSeamAns[i], verticalSeam[i]);
        Assertions.assertEquals(2414.9735, getEnergyOfSeam(carver, verticalSeam, VERTICAL));

        int[] horizontalSeam = carver.findHorizontalSeam();
        int[] horizontalSeamAns = new int[]{1, 2, 1, 2, 1, 0};
        for (int i = 0; i < horizontalSeam.length; i++)
            Assertions.assertEquals(horizontalSeamAns[i], horizontalSeam[i]);

        Assertions.assertEquals(2530.6820, getEnergyOfSeam(carver, horizontalSeam, HORIZONTAL));

        carver.removeVerticalSeam(verticalSeam);
        System.out.println(carver.picture().toString());

        //carver.removeHorizontalSeam(horizontalSeam);
        //System.out.println(carver.picture().toString());

        //int[] invalidSeam = new int[]{3, 4, 3, 2, 1, 1};
        //carver.removeVerticalSeam(invalidSeam);
        //invalidSeam = new int[]{3, 4, 3, 2, 1, 1};

    }

    @Test
    public void seamTest7x10() {
        Picture picture = new Picture("SeamCarving/testcases/7x10.png");
        SeamCarver carver = new SeamCarver(picture);

        int[] verticalSeam = carver.findVerticalSeam();
        int[] verticalSeamAns = new int[]{2, 3, 4, 3, 4, 3, 3, 2, 2, 1};
        for (int i = 0; i < verticalSeam.length; i++)
            Assertions.assertEquals(verticalSeamAns[i], verticalSeam[i]);
        Assertions.assertEquals(3443.1978, getEnergyOfSeam(carver, verticalSeam, VERTICAL));

        int[] horizontalSeam = carver.findHorizontalSeam();
        int[] horizontalSeamAns = new int[]{6, 7, 7, 7, 8, 8, 7};
        for (int i = 0; i < horizontalSeam.length; i++)
            Assertions.assertEquals(horizontalSeamAns[i], horizontalSeam[i]);

        Assertions.assertEquals(2898.3139, getEnergyOfSeam(carver, horizontalSeam, HORIZONTAL));
    }

    @Test
    public void seamTest10x12() {
        Picture picture = new Picture("SeamCarving/testcases/10x12.png");
        SeamCarver carver = new SeamCarver(picture);

        int[] verticalSeam = carver.findVerticalSeam();
        int[] verticalSeamAns = new int[]{5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5};
        for (int i = 0; i < verticalSeam.length; i++)
            Assertions.assertEquals(verticalSeamAns[i], verticalSeam[i]);
        Assertions.assertEquals(3599.0301, getEnergyOfSeam(carver, verticalSeam, VERTICAL));

        int[] horizontalSeam = carver.findHorizontalSeam();
        int[] horizontalSeamAns = new int[]{8, 9, 10, 10, 10, 9, 10, 10, 9, 8};
        for (int i = 0; i < horizontalSeam.length; i++)
            Assertions.assertEquals(horizontalSeamAns[i], horizontalSeam[i]);

        Assertions.assertEquals(3380.3042, getEnergyOfSeam(carver, horizontalSeam, HORIZONTAL));
    }

    private static double getEnergyOfSeam(SeamCarver carver, int[] seam, boolean direction) {
        double totalSeamEnergy = 0.0;

        for (int row = 0; row < carver.height(); row++) {
            for (int col = 0; col < carver.width(); col++) {
                double energy = carver.energy(col, row);
                if ((direction == HORIZONTAL && row == seam[col]) ||
                        (direction == VERTICAL && col == seam[row])) {
                    totalSeamEnergy += energy;
                }
            }
        }
        DecimalFormat df = new DecimalFormat("####0.0000");
        return Double.valueOf(df.format(totalSeamEnergy));
    }

}