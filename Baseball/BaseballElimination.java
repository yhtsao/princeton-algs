import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    /**
     * create a baseball division from given filename
     *
     * @param filename
     */
    public BaseballElimination(String filename) {

    }

    /**
     * number of teams
     *
     * @return
     */
    public int numberOfTeams() {
        return 0;
    }

    /**
     * all teams
     *
     * @return
     */
    public Iterable<String> teams() {
        return null;
    }

    /**
     * number of wins for given team
     *
     * @param team
     * @return
     */
    public int wins(String team) {
        return 0;
    }

    /**
     * number of losses for given team
     *
     * @param team
     * @return
     */
    public int losses(String team) {
        return 0;
    }

    /**
     * number of remaining games for given team
     *
     * @param team
     * @return
     */
    public int remaining(String team) {
        return 0;
    }

    /**
     * number of remaining games between team1 and team2
     *
     * @param team1
     * @param team2
     * @return
     */
    public int against(String team1, String team2) {
        return 0;
    }

    /**
     * is given team eliminated ?
     *
     * @param team
     * @return
     */
    public boolean isEliminated(String team) {
        return false;
    }

    /**
     * subset R of teams that eliminates given team; null if not eliminated
     *
     * @param team
     * @return
     */
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
