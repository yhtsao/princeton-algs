import edu.princeton.cs.algs4.Out;


public class DigraphGenerator {

    public static void main(String[] args) {
        String filename = "digraph_complete10.txt";
        int vertex = 10;
        int edge = 10 * 9 / 2;
        Out out = new Out(filename);
        out.print(vertex + "\n");
        out.print(edge + "\n");
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                if (i == j) continue;
                out.print(i + " " + j + "\n");
            }
        }
        out.close();
    }
}
