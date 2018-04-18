import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private Digraph G;
    private int ancestor;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     */
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();

        this.G = new Digraph(G);
        this.ancestor = -1;
    }

    /**
     * length of shortest ancestral path between v and w;
     * -1 if no such path
     */
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int minSmp = findShortestAncestor(bfsV, bfsW);

        return minSmp;
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral path;
     * -1 if no such path
     *
     * @param v
     * @param w
     * @return
     */
    public int ancestor(int v, int w) {
        if (length(v, w) < 0)
            return -1;
        return this.ancestor;
    }

    private int findShortestAncestor(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int sap = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            int path = bfsV.distTo(i) + bfsW.distTo(i);
            if (path < sap && path >= 0) {
                sap = path;
                this.ancestor = i;
            }
        }
        if (sap == Integer.MAX_VALUE) return -1;
        return sap;
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex in w;
     * -1 if no such path
     *
     * @param v
     * @param w
     * @return
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int minSmp = findShortestAncestor(bfsV, bfsW);


        return minSmp;
    }

    /**
     * a common ancestor that participates in shortest ancestral path;
     * -1 if no such path
     *
     * @param v
     * @param w
     * @return
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        if (length(v, w) < 0) return -1;
        return this.ancestor;
    }

}
