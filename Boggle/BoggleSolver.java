import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {

    private Set<String> validWord;
    private Trie<Integer> trie;

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     * (assume each word in the dictionary contains only the uppercase letters A through Z.)
     *
     * @param dictionary
     */
    public BoggleSolver(String[] dictionary) {
        this.trie = new Trie<>();
        for (String word : dictionary) {
            trie.put(word, 0);
        }
        this.validWord = new HashSet<>();
    }

    /**
     * Returns the set of all valid words in the given Boggle board, as an Iterable.
     *
     * @param board
     * @return
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException();

        this.validWord = new HashSet<>();

        runDfsToFindAllValidWord(board);

        return this.validWord;
    }

    private void runDfsToFindAllValidWord(BoggleBoard board) {
        boolean[][] isMarked = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); i++) {
            Arrays.fill(isMarked[i], false);
        }

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                dfs(board, i, j, isMarked, "", trie.root);
            }
        }
    }

    private void dfs(BoggleBoard board, int row, int col, boolean[][] isMarked,
                     String prefix, TrieNode currentNode) {
        if (row < 0 || col < 0 || row >= board.rows() || col >= board.cols()) return;
        if (isMarked[row][col]) return;

        Character c = board.getLetter(row, col);
        int d;
        if (c.equals('Q')) {
            prefix += "QU";
            d = prefix.length() - 2;
        } else {
            prefix += c;
            d = prefix.length() - 1;
        }

        TrieNode nextNode = getNextNode(currentNode, prefix, d);
        if (nextNode == null) return;

        isMarked[row][col] = true;
        dfs(board, row - 1, col - 1, isMarked, prefix, nextNode);
        dfs(board, row - 1, col, isMarked, prefix, nextNode);
        dfs(board, row - 1, col + 1, isMarked, prefix, nextNode);
        dfs(board, row, col - 1, isMarked, prefix, nextNode);
        dfs(board, row, col + 1, isMarked, prefix, nextNode);
        dfs(board, row + 1, col - 1, isMarked, prefix, nextNode);
        dfs(board, row + 1, col, isMarked, prefix, nextNode);
        dfs(board, row + 1, col + 1, isMarked, prefix, nextNode);
        isMarked[row][col] = false;
    }

    private TrieNode getNextNode(TrieNode currentNode, String prefix, int d) {
        TrieNode nextNode = trie.getNonRecursive(currentNode, prefix, d);
        if (nextNode != null && nextNode.val != null && prefix.length() > 2)
            validWord.add(prefix);
        return nextNode;
    }

    /**
     * Returns the score of the given word if it is in the dictionary, zero otherwise.
     * (assume the word contains only the uppercase letters A through Z.)
     *
     * @param word
     * @return
     */
    public int scoreOf(String word) {
        if (trie.contains(word)) {
            switch (word.length()) {
                case 0:
                case 1:
                case 2:
                    return 0;
                case 3:
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 5;
                default:
                    return 11;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}