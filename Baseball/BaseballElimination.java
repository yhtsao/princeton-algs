import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseballElimination {

    private class Team {
        String name;
        int win, loss, remain;
        int[] game;
        boolean isEliminated = false;
        List<String> certificateOfElimination;

        public Team(String name, int win, int loss, int remain) {
            this.name = name;
            this.win = win;
            this.loss = loss;
            this.remain = remain;
        }
    }

    private class Vertex {
        Team team, team1, team2;
    }

    private Map<String, Team> teamToStatusMap;
    private String[] teamList;
    private int numOfTeam;

    /**
     * create a baseball division from given filename
     *
     * @param filename
     */
    public BaseballElimination(String filename) {
        if (filename == null) throw new IllegalArgumentException();

        readTeamDetailFromFile(filename);

        for (int i = 0; i < numberOfTeams(); i++) {
            buildFlowNetworkAndFindMinCut(i);

        }
    }

    private void readTeamDetailFromFile(String filename) {
        In in = new In(filename);
        numOfTeam = in.readInt();
        teamList = new String[numOfTeam];
        teamToStatusMap = new HashMap<>();
        for (int i = 0; i < numOfTeam; i++) {
            // read {team, win, loss, remain}
            Team team = new Team(in.readString(), in.readInt(), in.readInt(), in.readInt());
            team.game = new int[numOfTeam];
            for (int j = 0; j < numOfTeam; j++) {
                team.game[j] = in.readInt();
            }
            teamList[i] = team.name;
            teamToStatusMap.put(team.name, team);
        }
    }

    private void buildFlowNetworkAndFindMinCut(int eliminatedTeamIndex) {
        if (isTrivialCase(eliminatedTeamIndex)) return;

        int numOfGameVertices = Combination.exec(numOfTeam - 1, 2);
        int numOfTeamVertices = numOfTeam - 1;
        int totalNumberOfVertices = 2 + numOfGameVertices + numOfTeamVertices;
        int s = 0, t = totalNumberOfVertices - 1;

        FlowNetwork flowNetwork = new FlowNetwork(totalNumberOfVertices);
        Vertex[] vertices = new Vertex[totalNumberOfVertices];

        // initialize team vertices
        for (int i = 0, j = 1; i < numOfTeam; i++) {
            if (i == eliminatedTeamIndex) continue;
            Vertex vertex = new Vertex();
            vertex.team = teamToStatusMap.get(teamList[i]);
            vertices[numOfGameVertices + j] = vertex;
            j++;
        }

        int gameVerticesIndex = 1;
        for (int i = 0; i < numOfTeam; i++) {
            if (i == eliminatedTeamIndex) continue;

            for (int j = i + 1; j < numOfTeam; j++) {
                if (j == eliminatedTeamIndex) continue;

                // build edges from source to game vertices
                Vertex vertex = new Vertex();
                vertex.team1 = teamToStatusMap.get(teamList[i]);
                vertex.team2 = teamToStatusMap.get(teamList[j]);
                vertices[gameVerticesIndex] = vertex;

                FlowEdge flowEdge = new FlowEdge(s, gameVerticesIndex, vertex.team1.game[j]);
                flowNetwork.addEdge(flowEdge);

                // build edges from game to team vertices
                for (int k = numOfGameVertices + 1; k < totalNumberOfVertices - 1; k++) {
                    if (vertices[k].team.name.equals(vertex.team1.name)
                            || vertices[k].team.name.equals(vertex.team2.name)) {
                        flowEdge = new FlowEdge(gameVerticesIndex, k, Double.POSITIVE_INFINITY);
                        flowNetwork.addEdge(flowEdge);
                    }
                }
                gameVerticesIndex++;
            }
        }

        // build team vertices to target vertex
        Team givenTeam = teamToStatusMap.get(teamList[eliminatedTeamIndex]);
        double maxWinOfGivenTeam = givenTeam.win + givenTeam.remain;
        for (int i = numOfGameVertices + 1; i < totalNumberOfVertices - 1; i++) {
            double capacity = maxWinOfGivenTeam - vertices[i].team.win;
            if (capacity < 0) capacity = 0;
            FlowEdge flowEdge = new FlowEdge(i, t, capacity);
            flowNetwork.addEdge(flowEdge);
        }

        // compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(flowNetwork, s, t);

        /**
         * Check if given team is eliminated by checking each edge from source to game vertices is full
         * if there exists one edge is not full, means that given team is eliminated
         * if it's eliminated, find all team vertices on source side as certificate team
         */
        for (FlowEdge e : flowNetwork.adj(s)) {
            if ((s == e.from()) && e.flow() != e.capacity()) {
                givenTeam.isEliminated = true;
                givenTeam.certificateOfElimination = new ArrayList<>();
                for (int v = numOfGameVertices + 1; v < totalNumberOfVertices - 1; v++) {
                    if (maxflow.inCut(v)) {
                        String certificateTeam = vertices[v].team.name;
                        givenTeam.certificateOfElimination.add(certificateTeam);
                    }
                }
                break;
            }
        }
    }

    private boolean isTrivialCase(int eliminatedTeamIndex) {
        Team givenTeam = teamToStatusMap.get(teamList[eliminatedTeamIndex]);
        for (int i = 0; i < numberOfTeams(); i++) {
            if (i == eliminatedTeamIndex) continue;
            Team otherTeam = teamToStatusMap.get(teamList[i]);
            if (givenTeam.win + givenTeam.remain < otherTeam.win) {
                givenTeam.isEliminated = true;
                givenTeam.certificateOfElimination = new ArrayList<>();
                givenTeam.certificateOfElimination.add(otherTeam.name);
                return true;
            }
        }
        return false;
    }

    /**
     * number of teams
     *
     * @return
     */
    public int numberOfTeams() {
        return numOfTeam;
    }

    /**
     * all teams
     *
     * @return
     */
    public Iterable<String> teams() {
        return teamToStatusMap.keySet();
    }

    /**
     * number of wins for given team
     *
     * @param team
     * @return
     */
    public int wins(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToStatusMap.get(team).win;
    }

    /**
     * number of losses for given team
     *
     * @param team
     * @return
     */
    public int losses(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToStatusMap.get(team).loss;
    }

    /**
     * number of remaining games for given team
     *
     * @param team
     * @return
     */
    public int remaining(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToStatusMap.get(team).remain;
    }

    /**
     * number of remaining games between team1 and team2
     *
     * @param team1
     * @param team2
     * @return
     */
    public int against(String team1, String team2) {
        if (!isValidTeam(team1) || !isValidTeam(team2))
            throw new IllegalArgumentException();
        for (int i = 0; i < numOfTeam; i++) {
            if (teamList[i].equals(team2))
                return teamToStatusMap.get(team1).game[i];
        }
        return 0;
    }

    /**
     * is given team eliminated ?
     *
     * @param team
     * @return
     */
    public boolean isEliminated(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToStatusMap.get(team).isEliminated;
    }

    /**
     * subset R of teams that eliminates given team; null if not eliminated
     *
     * @param team
     * @return
     */
    public Iterable<String> certificateOfElimination(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToStatusMap.get(team).certificateOfElimination;
    }

    private boolean isValidTeam(String team) {
        if (team == null || teamToStatusMap.get(team) == null)
            return false;
        return true;
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
