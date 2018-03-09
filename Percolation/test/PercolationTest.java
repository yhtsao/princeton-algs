import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {
    @Test
    public void test1() {
        In in = new In("percolation-test/input3.txt");      // input file
        runPercForFullTest(in, 4, 3, 1, false);
    }

    @Test
    public void test2() {
        In in = new In("percolation-test/input20.txt");      // input file
        runPercForFullTest(in, 231, 18, 1, false);
    }

    @Test
    public void test3() {
        In in = new In("percolation-test/input10.txt");      // input file
        runPercForFullTest(in, 56, 9, 1, false);
    }

    @Test
    public void test4() {
        In in = new In("percolation-test/input50.txt");      // input file
        runPercForFullTest(in, 1412, 22, 28, false);
    }

    @Test
    public void test5() {
        In in = new In("percolation-test/input6.txt");      // input file
        runPercForFullTest(in, 1, 1, 6, true);
    }

    @Test
    public void test6() {
        In in = new In("percolation-test/input10-no.txt");      // input file
        runPercForFullTest(in, 25, 1, 10, true);
    }

    private void runPercForFullTest(In in, int stopAtNumOfOpenSite, int p, int q, boolean expected) {
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            System.out.println("isFull: " + perc.isFull(i, j));
            System.out.println("percolate: " + perc.percolates());

            if (perc.numberOfOpenSites() == stopAtNumOfOpenSite) {
                Assertions.assertEquals(expected, perc.isFull(p, q));
            }
        }
    }
}