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
        Assertions.assertEquals(false, division.isEliminated("Montreal"));
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
}
