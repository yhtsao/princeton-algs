import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordNet;
    /**
     * constructor takes a WordNet object
     *
     * @param wordnet
     */
    public Outcast(WordNet wordnet) {
        if (wordnet == null) throw new IllegalArgumentException();
        this.wordNet = wordnet;
    }

    /**
     * given an array of WordNet nouns, return an outcast
     *
     * @param nouns
     * @return
     */
    public String outcast(String[] nouns) {
        if (nouns == null || nouns.length == 0) throw new IllegalArgumentException();

        int[][] dists = new int[nouns.length][nouns.length];
        int maxDist = Integer.MIN_VALUE;
        String outcast = null;
        for (int i = 0; i < nouns.length; i++) {
            String nounA = nouns[i];
            int distSum = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) continue;

                String nounB = nouns[j];
                int distAB = dists[i][j];
                if (distAB == 0) {
                    distAB = wordNet.distance(nounA, nounB);
                    dists[i][j] = distAB;
                    dists[j][i] = distAB;
                }
                distSum += distAB;
            }
            if (distSum > maxDist) {
                maxDist = distSum;
                outcast = nounA;
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}