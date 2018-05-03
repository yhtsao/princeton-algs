import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SeamCarverTest {
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;
    private String[] pictureList = new String[]{
            "SeamCarving/testcases/3x4.png"
            //"SeamCarving/testcases/3x7.png",
            //"SeamCarving/testcases/4x6.png",
            //"SeamCarving/testcases/5x6.png",
            //"SeamCarving/testcases/6x5.png",
            //"SeamCarving/testcases/7x3.png",
            //"SeamCarving/testcases/7x10.png",
            //"SeamCarving/testcases/10x10.png",
            //"SeamCarving/testcases/10x12.png",
            //"SeamCarving/testcases/12x10.png"
    };

    @Test
    public void seamTest() {

        for (String pictureFile : pictureList) {
            StdOut.println(pictureFile);
            Picture picture = new Picture(pictureFile);
            SeamCarver carver = new SeamCarver(picture);

            int[] verticalSeam = carver.findVerticalSeam();
            int[] verticalSeamAns = new int[]{0, 1, 1, 0};
            for (int i = 0; i < verticalSeam.length; i++)
                Assertions.assertEquals(verticalSeamAns[i], verticalSeam[i]);
            Assertions.assertEquals(2456.6156, getEnergyOfSeam(carver, verticalSeam, VERTICAL));

            int[] horizontalSeam = carver.findHorizontalSeam();
            int[] horizontalSeamAns = new int[]{1, 2, 1};
            for (int i = 0; i < verticalSeam.length; i++)
                Assertions.assertEquals(horizontalSeamAns[i], horizontalSeam[i]);

            Assertions.assertEquals(2228.087702, getEnergyOfSeam(carver, horizontalSeam, HORIZONTAL));
        }
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
        return totalSeamEnergy;
    }

    private void checkSeam(String pictureFile) {
        String ansPath = getAnsFilepath(pictureFile);
        In in = new In(ansPath);
        String verticalRegex = "Vertical seam: \\{ (.*) }";

        while (in.hasNextLine()) {
            String line = in.readLine();
            StdOut.println(line);
            Pattern pattern = Pattern.compile(verticalRegex);

            Matcher matcher = pattern.matcher(line);

            if (matcher.matches()) {
                StdOut.println(matcher.group(1));
            }

        }


        Picture picture = new Picture(pictureFile);
        //SeamCarver carver = new SeamCarver(picture);

    }

    private String getAnsFilepath(String pictureFile) {

        // "SeamCarving/testcases/3x4.png" -> "testcases/3x4.png"
        String ans = StringUtils.substringAfter(pictureFile, "/");

        // "testcases/3x4.png" -> "testcases/3x4.printseams.txt"
        StringUtils.replace(ans, "png", "printseams.txt");

        return ans;
    }
}