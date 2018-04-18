public class WordNet {

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {

    }

    /**
     * returns all WordNet nouns
     *
     * @return
     */
    public Iterable<String> nouns() {
        return null;
    }

    /**
     * is the word a WordNet noun?
     */
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();

        return false;
    }

    /**
     * distance between nounA and nounB (defined below)
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();

        return 0;
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

        return null;
    }
}
