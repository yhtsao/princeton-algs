import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}