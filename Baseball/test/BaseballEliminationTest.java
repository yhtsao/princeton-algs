import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BaseballEliminationTest {
    @Test
    public void teams4() {
        String filename = "testcases/teams4.txt";
        BaseballElimination division = new BaseballElimination(filename);
        Assertions.assertEquals(false, division.isEliminated("Atlanta"));
        Assertions.assertEquals(true, division.isEliminated("Philadelphia"));
        Assertions.assertEquals(false, division.isEliminated("New_York"));
        Assertions.assertEquals(true, division.isEliminated("Montreal"));

        Assertions.assertEquals(1, division.against("Atlanta", "Philadelphia"));
    }

    @Test
    public void teams4a() {
        String filename = "testcases/teams4a.txt";
        BaseballElimination division = new BaseballElimination(filename);
        Assertions.assertEquals(true, division.isEliminated("Ghaddafi"));
    }

    @Test
    public void teams5() {
        String filename = "testcases/teams5.txt";
        BaseballElimination division = new BaseballElimination(filename);
        Assertions.assertEquals(true, division.isEliminated("Detroit"));
    }

    @Test
    public void teams48() {
        String filename = "testcases/teams48.txt";
        BaseballElimination division = new BaseballElimination(filename);
        Assertions.assertEquals(true, division.isEliminated("Team6"));
        Assertions.assertEquals(true, division.isEliminated("Team23"));
        Assertions.assertEquals(true, division.isEliminated("Team47"));

        Assertions.assertEquals(false, division.isEliminated("Team42"));
        Assertions.assertEquals(false, division.isEliminated("Team28"));
        Assertions.assertEquals(false, division.isEliminated("Team15"));

    }
}
