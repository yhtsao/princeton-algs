import java.util.Arrays;
import java.util.Stack;

public class Board {

    private int dimension;
    private int[][] blocks;

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

        this.blocks = new int[dimension][];
        for (int i = 0; i < dimension; i++) {
            this.blocks[i] = Arrays.copyOf(blocks[i], dimension);
        }
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
                if (blocks[i][j] != i * dimension + j + 1 && !isBlank(i, j)) {
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
                if (blocks[i][j] != i * dimension + j + 1 && !isBlank(i, j)) {
                    int correctRow = (blocks[i][j] - 1) / dimension;
                    int correctCol = (blocks[i][j] - 1) % dimension;
                    count += Math.abs(correctRow - i) + Math.abs(correctCol - j);
                }
            }
        }
        return count;
    }

    private boolean isBlank(int row, int col) {
        if (row < 0 || row >= dimension)
            return true;
        else if (col < 0 || col >= dimension)
            return true;
        else if (blocks[row][col] == 0)
            return true;
        return false;
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        return (hamming() == 0);
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        int[][] twinBlock = arrayCopy(blocks);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (!isBlank(i, j) && !isBlank(i, j + 1)) {
                    swapArrVal(twinBlock, i, j, i, j + 1);
                    Board twinBoard = new Board(twinBlock);
                    return twinBoard;
                }
            }
        }
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
        if (y == null) return false;
        if (y.getClass() != Board.class) return false;
        Board board = (Board) y;
        return dimension == board.dimension &&
                isTwoDimArrayEqual(blocks, board.blocks);
    }

    private boolean isTwoDimArrayEqual(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) {
                return false;
            }
        }
        return true;
    }
    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<>();

        // find blank position
        int blankRow = 0, blankCol = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
        }

        // move blank position
        if (blankRow - 1 >= 0) {
            Board board = moveBlank(blankRow - 1, blankCol, blankRow, blankCol);
            stack.push(board);
        }
        if (blankRow + 1 < dimension) {
            Board board = moveBlank(blankRow + 1, blankCol, blankRow, blankCol);
            stack.push(board);
        }
        if (blankCol - 1 >= 0) {
            Board board = moveBlank(blankRow, blankCol - 1, blankRow, blankCol);
            stack.push(board);
        }
        if (blankCol + 1 < dimension) {
            Board board = moveBlank(blankRow, blankCol + 1, blankRow, blankCol);
            stack.push(board);
        }

        return stack;
    }

    private Board moveBlank(int aRow, int aCol, int bRow, int bCol) {
        int[][] movedBlocks = arrayCopy(this.blocks);
        swapArrVal(movedBlocks, aRow, aCol, bRow, bCol);
        Board board = new Board(movedBlocks);
        return board;
    }

    private int[][] arrayCopy(int[][] array) {
        int n = array.length;
        int[][] out = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                out[i][j] = array[i][j];
        }
        return out;
    }

    private void swapArrVal(int[][] arr, int row1, int col1, int row2, int col2) {
        int temp = arr[row1][col1];
        arr[row1][col1] = arr[row2][col2];
        arr[row2][col2] = temp;
    }

    /**
     * string representation of this board (in the output format specified below)
     *
     * @return
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
