public class Board {
    private int dimension;
    
    /**
     * construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null || blocks.length == 0)
            return null;
        this.dimension = blocks.length;
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
     * number of blocks out of place
     *
     * @return
     */
    public int hamming() {
        return 0;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
        return 0;
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        return false;
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
        return false;
    }

    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        return null;
    }

    /**
     * string representation of this board (in the output format specified below)
     *
     * @return
     */
    public String toString() {
        return null;
    }
}
