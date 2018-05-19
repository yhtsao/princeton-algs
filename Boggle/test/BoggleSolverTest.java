import edu.princeton.cs.algs4.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class BoggleSolverTest {
    @Test
    public void dicAlgs4withBoard4() {
        In in = new In("testcases/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("testcases/board4x4.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        Assertions.assertEquals(33, score);
    }

    @Test
    public void dicAlgs4withBoardQ() {
        In in = new In("testcases/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("testcases/board-q.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        Assertions.assertEquals(84, score);
    }

    @Test
    public void dicYawlwithBoard26539() {
        In in = new In("testcases/dictionary-yawl.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("testcases/board-points26539.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        Assertions.assertEquals(26539, score);
    }

    @Test
    public void contructTest() {
        In in = new In("testcases/dictionary-zingarelli2005.txt");
        String[] dictionary = in.readAllStrings();
        long start = System.currentTimeMillis();
        BoggleSolver solver = new BoggleSolver(dictionary);
        long end = System.currentTimeMillis();
        StdOut.println((end - start));
    }

    @Test
    public void trieTest() {
        In in = new In("testcases/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        Trie<Integer> tst = new Trie<>();
        for (String word : dictionary) {
            tst.put(word, 0);
        }
        StdOut.println(tst.contains("WALKED"));
    }

    @Test
    public void getAllPossibleWord() {
        In in = new In("testcases/dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("testcases/board2x2.txt");
        Iterator<String> iter = solver.getAllValidWords(board).iterator();
        while (iter.hasNext())
            StdOut.println(iter.next());
    }
}