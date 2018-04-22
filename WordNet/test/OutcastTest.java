import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OutcastTest {
    @Test
    public void test() {
        String synsets = "testcases/synsets.txt";
        String hypernyms = "testcases/hypernyms.txt";
        WordNet wordnet = new WordNet(synsets, hypernyms);
        Outcast outcast = new Outcast(wordnet);

        String outcastFile = "testcases/outcast5.txt";
        In in = new In(outcastFile);
        String[] nouns = in.readAllStrings();
        Assertions.assertEquals("table", outcast.outcast(nouns));

        outcastFile = "testcases/outcast8.txt";
        in = new In(outcastFile);
        nouns = in.readAllStrings();
        Assertions.assertEquals("bed", outcast.outcast(nouns));

        outcastFile = "testcases/outcast11.txt";
        in = new In(outcastFile);
        nouns = in.readAllStrings();
        Assertions.assertEquals("potato", outcast.outcast(nouns));
    }
}