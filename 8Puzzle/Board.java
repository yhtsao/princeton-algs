import java.util.Arrays;
import java.util.Iterator;

public class Board {
    private final int goalBlock[][];
    private int dimension;
    private int blocks[][];

    /**
     * construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null || blocks.length == 0)
            throw new IllegalArgumentException();
        this.dimension = blocks.length;
        this.blocks = blocks;

        this.goalBlock = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++)
                goalBlock[i][j] = i * dimension + j + 1;
        }
        goalBlock[dimension - 1][dimension - 1] = 0;
    }

    /**
     * board dimension n
     *
     * @return
     */
    public int dimension() {
        return dimension;
    }

    /**
     * number of blocks out of position
     *
     * @return
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != goalBlock[i][j] && blocks[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != goalBlock[i][j] && blocks[i][j] != 0) {
                    int correctRow = (blocks[i][j] - 1) / dimension;
                    int correctCol = (blocks[i][j] - 1) % dimension;
                    count += Math.abs(correctRow - i) + Math.abs(correctCol - j);
                }
            }
        }
        return count;
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        return Arrays.equals(blocks, goalBlock);
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        return null;
    }

    /**
     * does this board equal y?
     *
     * @param y
     * @return
     */
    public boolean equals(Object y) {
        if (this == y) return true;
        if (!(y instanceof Board)) return false;
        Board board = (Board) y;
        return dimension == board.dimension &&
                Arrays.equals(blocks, board.blocks);
    }

    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return null;
            }
        };
    }

    /**
     * string representation of this board (in the output format specified below)
     *
     * @return
     */
    public String toString() {
        String str = dimension + "\n";
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                str = str + blocks[i][j] + " ";
            }
            str += "\n";
        }
        return str;
    }
}
