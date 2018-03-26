import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {

    private class SearchNode {
        public SearchNode predecessor;
        public Board board;
        public int manhattan;
        public int priority;
        public int moves;


        public SearchNode(SearchNode predecessor, Board board, int moves) {
            this.predecessor = predecessor;
            this.board = board;
            this.manhattan = board.manhattan();
            this.moves = moves;
            this.priority = manhattan + moves;
        }

        public int compareTo(SearchNode that) {
            if (this.priority == that.priority) {
                return 0;
            } else if (this.priority > that.priority)
                return 1;
            else
                return -1;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (obj.getClass() != SearchNode.class) return false;
            SearchNode that = (SearchNode) obj;
            return this.board.equals(that.board);
        }
    }

    private Stack<Board> solution;
    private int moves;
    private boolean isSolvable;

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param initial
     */
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();



        this.solution = new Stack<>();
        this.moves = 0;
        this.isSolvable = false;

        solvePuzzle(initial);
    }

    private void solvePuzzle(Board init) {
        // initial search node of input board
        SearchNode primaryNode = new SearchNode(null, init, 0);
        MinPQ<SearchNode> primaryMinPQ = new MinPQ<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return o1.compareTo(o2);
            }
        });

        // twin board for checking solvable
        Board twinBoard = init.twin();
        SearchNode twinNode = new SearchNode(null, twinBoard, 0);
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return o1.compareTo(o2);
            }
        });

        while (!primaryNode.board.isGoal() && !twinNode.board.isGoal()) {
            // do primary search
            addNeighborToMinPQ(primaryNode, primaryMinPQ);
            primaryNode = primaryMinPQ.delMin();

            // do search for twin board
            addNeighborToMinPQ(twinNode, twinMinPQ);
            twinNode = twinMinPQ.delMin();
        }

        if (twinNode.board.isGoal()) {
            isSolvable = false;
            return;
        }

        this.isSolvable = true;
        this.moves = primaryNode.moves;
        while (primaryNode != null) {
            solution.push(primaryNode.board);
            primaryNode = primaryNode.predecessor;
        }
    }

    private void addNeighborToMinPQ(SearchNode current, MinPQ<SearchNode> minPQ) {
        Board preBoard = null;
        if (current.predecessor != null)
            preBoard = current.predecessor.board;
        Board currentBoard = current.board;
        Iterator<Board> iter = currentBoard.neighbors().iterator();
        while (iter.hasNext()) {
            Board neighbor = iter.next();
            if (neighbor.equals(preBoard))
                continue;
            SearchNode searchNode = new SearchNode(current, neighbor, current.moves + 1);
            minPQ.insert(searchNode);
        }
    }

    /**
     * is the initial board solvable?
     *
     * @return
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     *
     * @return
     */
    public int moves() {
        if (!isSolvable())
            return -1;
        return moves;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     *
     * @return
     */
    public Iterable<Board> solution() {
        if (isSolvable)
            return solution;
        else
            return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
