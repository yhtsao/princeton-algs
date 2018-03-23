import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    public void test() {
        String file = "../testcases/puzzle04.txt";
        In in = new In(file);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
    }
}