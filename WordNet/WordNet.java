import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

public class WordNet {
    private Map<String, Bag<Integer>> synsetTable = new HashMap<>();
    private Map<Integer, String> synsetIdToNounsMap = new HashMap<>();
    private Digraph G;
    private SAP sap;
    private int numOfSynsetId;

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {

        buildSynsetTable(synsets);

        buildHypernym(hypernyms);

        this.sap = new SAP(G);
    }

    /**
     * Build synset table by parsing each synset to "id" and "nouns"
     * use the noun as key to store id into map
     * Ex: worm -> (81679, 81680)
     *
     * @param synsets
     */
    private void buildSynsetTable(String synsets) {
        this.numOfSynsetId = 0;
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] arr = line.split(",");
            int synsetId = Integer.parseInt(arr[0]);

            synsetIdToNounsMap.put(synsetId, arr[1]);

            String[] nouns = arr[1].split(" ");
            for (String noun : nouns) {
                updateSynsetTable(noun, synsetId);
            }
            numOfSynsetId++;
        }
    }

    private void updateSynsetTable(String noun, int synsetId) {
        Bag<Integer> synsetIds = synsetTable.get(noun);
        if (synsetIds == null) {
            synsetIds = new Bag<>();
            synsetIds.add(synsetId);
            synsetTable.put(noun, synsetIds);
        } else {
            synsetIds.add(synsetId);
        }
    }

    /**
     * Build digraph by given hypernym
     *
     * @param hypernyms
     */
    private void buildHypernym(String hypernyms) {
        this.G = new Digraph(numOfSynsetId);
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] arr = in.readLine().split(",");
            int v = Integer.parseInt(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                G.addEdge(v, Integer.parseInt(arr[i]));
            }
        }
        if (!isRootedDAG()) throw new IllegalArgumentException();
    }

    /**
     * Chech outdegree of each vertex in Digraph
     * if there is one vertex has outdegree == 0,
     * this Digraph is rooted DAG
     *
     * @return
     */
    private boolean isRootedDAG() {
        int numOfRoot = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0)
                numOfRoot++;
        }
        return (numOfRoot == 1);
    }

    /**
     * returns all WordNet nouns
     *
     * @return
     */
    public Iterable<String> nouns() {
        return synsetTable.keySet();
    }

    /**
     * is the word a WordNet noun?
     */
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return (synsetTable.get(word) != null);
    }

    /**
     * distance between nounA and nounB
     * distance(A, B) = distance is the minimum length of
     * any ancestral path between any synset v of A and any synset w of B.
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        int minSmp = sap.length(synsetTable.get(nounA), synsetTable.get(nounB));
        return minSmp;
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     * in a shortest ancestral path (defined below)
     *
     * @param nounA
     * @param nounB
     * @return
     */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        int ancestorId = sap.ancestor(synsetTable.get(nounA), synsetTable.get(nounB));
        String ancestorNouns = synsetIdToNounsMap.get(ancestorId);
        return ancestorNouns;
    }
}
