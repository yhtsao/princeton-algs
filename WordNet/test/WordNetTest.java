import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordNetTest {
    @Test
    public void synsets15() {
        String synset = "testcases/synsets15.txt";
        String hypernyms = "testcases/hypernyms15Path.txt";
        WordNet wordNet = new WordNet(synset, hypernyms);
        Assertions.assertEquals(1, wordNet.distance("a", "b"));
    }
}