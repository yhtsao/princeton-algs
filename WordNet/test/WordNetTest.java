import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class WordNetTest {

    @Test
    public void synsets() {
        String synset = "testcases/synsets.txt";
        String hypernyms = "testcases/hypernyms.txt";
        WordNet wordNet = new WordNet(synset, hypernyms);
        Assertions.assertTrue(wordNet.isNoun("word"));
        Assertions.assertFalse(wordNet.isNoun("ssssnncns"));

        Assertions.assertEquals(13, wordNet.distance("virgule", "order_Picariae"));

        Assertions.assertEquals(14, wordNet.distance("Hizb_ut-Tahrir", "Haute-Normandie"));
        Assertions.assertEquals("entity", wordNet.sap("Hizb_ut-Tahrir", "Haute-Normandie"));
    }

    @Test
    public void synsets15() {
        String synset = "testcases/synsets15.txt";
        String hypernyms = "testcases/hypernyms15Path.txt";
        WordNet wordNet = new WordNet(synset, hypernyms);
        Assertions.assertEquals(1, wordNet.distance("a", "b"));
    }

    @Test
    void synsets1000_subgraph() {
        String synset = "testcases/synsets1000-subgraph.txt";
        String hypernyms = "testcases/hypernyms1000-subgraph.txt";
        WordNet wordNet = new WordNet(synset, hypernyms);
        Assertions.assertEquals(4, wordNet.distance("paraffin_scale", "essential_oil"));
        Assertions.assertEquals("lipid lipide lipoid", wordNet.sap("paraffin_scale", "essential_oil"));

    }

    @Test
    public void isNounTest() {
        String synset = "testcases/synsets.txt";
        String hypernyms = "testcases/hypernyms.txt";
        WordNet wordNet = new WordNet(synset, hypernyms);
        Assertions.assertTrue(wordNet.isNoun("word"));
        Assertions.assertTrue(wordNet.isNoun("Sir_Mortimer_Wheeler"));
        Assertions.assertFalse(wordNet.isNoun("Sir_Mortimer_Wheeler_123"));
        Assertions.assertFalse(wordNet.isNoun("ssssnncns"));

        Iterator<String> iter = wordNet.nouns().iterator();
        int numOfNouns = 0;
        while (iter.hasNext()) {
            iter.next();
            numOfNouns++;
        }
        Assertions.assertEquals(119188, numOfNouns);
    }

    @Test
    public void isRootDAG() {
        String synset = "testcases/synsets.txt";
        String[] hypernymsSet = new String[]{"testcases/hypernyms3InvalidTwoRoots.txt",
                "testcases/hypernyms6InvalidTwoRoots.txt",
                "testcases/hypernyms3InvalidCycle.txt",
                "testcases/hypernyms6InvalidCycle+Path.txt",};
        WordNet wordNet = null;

        for (String hypernyms : hypernymsSet) {
            try {
                wordNet = new WordNet(synset, hypernyms);
            } catch (IllegalArgumentException e) {
                Assertions.assertNull(wordNet);
            }
        }

    }
}