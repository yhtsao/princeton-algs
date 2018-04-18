import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SAPTest {
    @Test
    public void digraph1() {
        String filename = "testcases/digraph1.txt";
        In in = new In(filename);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        Assertions.assertEquals(0, sap.length(3, 3));

        Assertions.assertEquals(4, sap.length(3, 11));
        Assertions.assertEquals(1, sap.ancestor(3, 11));

        Assertions.assertEquals(3, sap.length(9, 12));
        Assertions.assertEquals(5, sap.ancestor(9, 12));

        Assertions.assertEquals(4, sap.length(7, 2));
        Assertions.assertEquals(0, sap.ancestor(7, 2));

        Assertions.assertEquals(-1, sap.length(1, 6));
        Assertions.assertEquals(-1, sap.ancestor(1, 6));
    }

    @Test
    public void digraph3() {
        String filename = "testcases/digraph3.txt";
        In in = new In(filename);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        Assertions.assertEquals(-1, sap.length(3, 11));
        Assertions.assertEquals(-1, sap.ancestor(3, 11));

        Assertions.assertEquals(1, sap.length(3, 4));
        Assertions.assertEquals(4, sap.ancestor(3, 4));

        Assertions.assertEquals(-1, sap.length(7, 4));

        Assertions.assertEquals(-1, sap.length(13, 3));

    }

    @Test
    public void digraphComplete10() {
        String filename = "testcases/digraph_complete10.txt";
        In in = new In(filename);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        //for (int i = 0; i < 100; i++) {
        int v = StdRandom.uniform(10);
        int w = StdRandom.uniform(10);
        System.out.println(v + ", " + w);
        //BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, w);
        //if (bfs.hasPathTo(v))
        //    System.out.println(bfs.distTo(v));
        sap.length(v, w);
        //}
    }

    @Test
    public void bfs() {
        String filename = "testcases/digraph1.txt";
        In in = new In(filename);
        Digraph G = new Digraph(in);

        System.out.println(G.adj(0).toString());
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, 12);
        System.out.println(bfs.distTo(8));
        System.out.println(bfs.distTo(5));

    }
}