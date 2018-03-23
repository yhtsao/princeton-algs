import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    public void puzzle04() {
        Board initial = initBoardFromFile("testcases/puzzle04.txt");

        Assertions.assertFalse(initial.isGoal());

        System.out.println(initial.toString());

        Assertions.assertEquals(4, initial.hamming());
        Assertions.assertEquals(4, initial.manhattan());
    }


    private Board initBoardFromFile(String filepath) {
        String file = filepath;
        In in = new In(file);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        return initial;
    }
}