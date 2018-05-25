import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CircularSuffixArrayTest {
    @Test
    public void test() {
        String str = "ABRACADABRA!";
        CircularSuffixArray suffixArray = new CircularSuffixArray(str);
        int[] indexAns = new int[]{11, 10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2};
        Assertions.assertEquals(str.length(), suffixArray.length());
        for (int i = 0; i < str.length(); i++) {
            Assertions.assertEquals(indexAns[i], suffixArray.index(i));
        }
    }

    @Test
    public void test2() {
        String str = "BBAABBBAAA";
        CircularSuffixArray suffixArray = new CircularSuffixArray(str);
        Assertions.assertEquals(str.length(), suffixArray.length());

        for (int i = 0; i < str.length(); i++) {
            StdOut.println(str.substring(i) + str.substring(0, i));
        }
        StdOut.println("--");
        for (int i = 0; i < str.length(); i++) {
            int index = suffixArray.index(i);
            StdOut.println(str.substring(index) + str.substring(0, index));
        }
        Assertions.assertEquals(7, suffixArray.index(0));
    }

    @Test
    public void test3() {
        String str = "ABBABABAAB";
        CircularSuffixArray suffixArray = new CircularSuffixArray(str);
        Assertions.assertEquals(str.length(), suffixArray.length());

        for (int i = 0; i < str.length(); i++) {
            StdOut.println(str.substring(i) + str.substring(0, i));
        }
        StdOut.println("--");
        for (int i = 0; i < str.length(); i++) {
            int index = suffixArray.index(i);
            StdOut.println(str.substring(index) + str.substring(0, index));
        }
        Assertions.assertEquals(5, suffixArray.index(1));
    }

    @Test
    public void testBinary() {
        In in = new In("testcases/rand10K.bin");
        String str = in.readAll();

        CircularSuffixArray suffixArray = new CircularSuffixArray(str);
        for (int i = 0; i < str.length(); i++) {
            StdOut.println(suffixArray.index(i));
        }
    }
}