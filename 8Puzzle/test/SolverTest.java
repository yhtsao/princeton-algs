import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Iterator;

class SolverTest {

    @Test
    public void puzzle04() {
        String filepath = "testcases/puzzle04.txt";
        Board board = initBoardFromFile(filepath);
        Solver solver = new Solver(board);
        Assertions.assertTrue(solver.isSolvable());
        Assertions.assertEquals(4, solver.moves());

        Iterator<Board> solution = solver.solution().iterator();
        while (solution.hasNext())
            System.out.println(solution.next());
    }

    @Test
    public void puzzle11() {
        String filepath = "testcases/puzzle11.txt";
        Board board = initBoardFromFile(filepath);
        Solver solver = new Solver(board);
        Assertions.assertTrue(solver.isSolvable());
        Assertions.assertEquals(11, solver.moves());
        System.out.println("Minimum number of moves = " + solver.moves());

        for (Board tmp : solver.solution())
            StdOut.println(tmp);
    }

    @Test
    public void solvableCases() {
        String filenamePrefix = "testcases/puzzle";
        for (int i = 0; i < 32; i++) {
            DecimalFormat formatter = new DecimalFormat("00");
            String filename = filenamePrefix + formatter.format(i) + ".txt";
            System.out.println(filename);
            Board board = initBoardFromFile(filename);
            Solver solver = new Solver(board);
            Assertions.assertTrue(solver.isSolvable());
            Assertions.assertEquals(i, solver.moves());
        }
    }

    @Test
    public void unsolvableCases() {
        File dir = new File("8Puzzle/testcases");
        File[] files = dir.listFiles();
        for (File file : files) {
            if (!file.getName().contains("unsolvable"))
                continue;
            String filepath = dir.getName() + "/" + file.getName();
            System.out.println(filepath);
            Board board = initBoardFromFile(filepath);
            Solver solver = new Solver(board);
            Assertions.assertFalse(solver.isSolvable());
        }
    }

    @Test
    public void puzzle01() {
        Board board = initBoardFromFile("testcases/puzzle01.txt");
        Solver solver = new Solver(board);
        Assertions.assertEquals(1, solver.moves());
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