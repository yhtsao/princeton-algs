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
}