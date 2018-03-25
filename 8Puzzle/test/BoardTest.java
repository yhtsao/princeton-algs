import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class BoardTest {

    @Test
    public void checkImmutable() {
        int[][] blocks = readValFromFile("testcases/puzzle04.txt");
        Board initial = new Board(blocks);
        int hamming = initial.hamming();
        int manhattan = initial.manhattan();

        int temp = blocks[0][0];
        blocks[0][0] = blocks[0][1];
        blocks[0][1] = temp;
        Assertions.assertEquals(hamming, initial.hamming());
        Assertions.assertEquals(manhattan, initial.manhattan());
    }

    @Test
    public void puzzle04() {
        Board initial = initBoardFromFile("testcases/puzzle04.txt");
        Board sameBoard = initBoardFromFile("testcases/puzzle04.txt");

        Assertions.assertTrue(initial.equals(sameBoard));
        Assertions.assertFalse(initial.isGoal());

        System.out.println(initial.toString());
        Assertions.assertEquals(3, initial.dimension());
        Assertions.assertEquals(4, initial.hamming());
        Assertions.assertEquals(4, initial.manhattan());

        Iterator<Board> iter = initial.neighbors().iterator();
        while (iter.hasNext())
            System.out.println(iter.next().toString());

        System.out.println("-------- twin --------");
        for (int i = 0; i < 2; i++) {
            Board twin = initial.twin();
            System.out.println(twin.toString());
        }
    }

    @Test
    public void puzzle00() {
        Board initial = initBoardFromFile("testcases/puzzle00.txt");

        Assertions.assertTrue(initial.isGoal());

        System.out.println(initial.toString());
    }

    @Test
    public void puzzle3x300() {
        Board initial = initBoardFromFile("testcases/puzzle3x3-00.txt");
        Assertions.assertTrue(initial.isGoal());
        System.out.println(initial.toString());
    }

    private int[][] readValFromFile(String filepath) {
        String file = filepath;
        In in = new In(file);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        return blocks;
    }

    private Board initBoardFromFile(String filepath) {
        int[][] blocks = readValFromFile(filepath);
        Board initial = new Board(blocks);
        return initial;
    }
}