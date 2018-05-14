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

    private class FlowVertex {
        int vertex;
        int team, team1, team2;
    }

    private Map<String, Team> teamToDetailMap;
    private List<String> teamList;
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
        teamList = new ArrayList<>();
        teamToDetailMap = new HashMap<>();
        for (int i = 0; i < numOfTeam; i++) {
            Team team = new Team(in.readString(), in.readInt(), in.readInt(), in.readInt());
            team.game = new int[numOfTeam];
            for (int j = 0; j < numOfTeam; j++) {
                team.game[j] = in.readInt();
            }
            teamList.add(team.name);
            teamToDetailMap.put(team.name, team);
        }
    }

    private FlowNetwork buildFlowNetworkAndFindMinCut(int teamIndex) {
        if (isTrivialCase(teamIndex)) return null;

        int numOfGameVertices = Combination.exec(numOfTeam - 1, 2);
        int numOfTeamVertices = numOfTeam - 1;
        int totalNumberOfVertices = 2 + numOfGameVertices + numOfTeamVertices;
        int s = 0, t = totalNumberOfVertices - 1;

        FlowNetwork flowNetwork = new FlowNetwork(totalNumberOfVertices);
        FlowVertex[] flowVertexs = new FlowVertex[totalNumberOfVertices];

        for (int i = 0, j = 1; i < numOfTeam; i++) {
            if (i == teamIndex) continue;
            FlowVertex flowVertex = new FlowVertex();
            flowVertex.team = i;
            flowVertexs[numOfGameVertices + j] = flowVertex;
            j++;
        }

        int currentIndex = 1;
        // build source to game vertices edges
        for (int i = 0; i < numOfTeam; i++) {
            if (i == teamIndex) continue;

            for (int j = i + 1; j < numOfTeam; j++) {
                if (j == teamIndex) continue;

                // build source to game vertices edges
                FlowVertex flowVertex = new FlowVertex();
                flowVertex.team1 = i;
                flowVertex.team2 = j;

                flowVertexs[currentIndex] = flowVertex;

                FlowEdge flowEdge = new FlowEdge(s, currentIndex, teamToDetailMap.get(teamList.get(i)).game[j]);
                flowNetwork.addEdge(flowEdge);

                // build game to team vertices edges
                for (int k = numOfGameVertices + 1; k < totalNumberOfVertices - 1; k++) {
                    if (flowVertexs[k].team == flowVertex.team1 || flowVertexs[k].team == flowVertex.team2) {
                        flowEdge = new FlowEdge(currentIndex, k, Double.POSITIVE_INFINITY);
                        flowNetwork.addEdge(flowEdge);
                    }
                }
                currentIndex++;
            }
        }

        // build team vertices to target edges
        Team givenTeam = teamToDetailMap.get(teamList.get(teamIndex));
        double maxWinOfGivenTeam = givenTeam.win + givenTeam.remain;
        for (int i = numOfGameVertices + 1; i < totalNumberOfVertices - 1; i++) {
            double capacity = maxWinOfGivenTeam - teamToDetailMap.get(teamList.get(flowVertexs[i].team)).win;
            if (capacity < 0) capacity = 0;
            FlowEdge flowEdge = new FlowEdge(i, t, capacity);
            flowNetwork.addEdge(flowEdge);
        }

        // compute maximum flow and minimum cut
        FordFulkerson maxflow = new FordFulkerson(flowNetwork, s, t);

        // check if edges from source to game vertices is full
        for (FlowEdge e : flowNetwork.adj(s)) {
            if ((s == e.from()) && e.flow() != e.capacity()) {
                givenTeam.isEliminated = true;
                givenTeam.certificateOfElimination = new ArrayList<>();
                for (int v = numOfGameVertices + 1; v < totalNumberOfVertices - 1; v++) {
                    if (maxflow.inCut(v)) {
                        String certificateTeam = teamList.get(flowVertexs[v].team);
                        givenTeam.certificateOfElimination.add(certificateTeam);
                    }
                }
                break;
            }
        }
        return null;
    }

    private boolean isTrivialCase(int teamIndex) {
        Team givenTeam = teamToDetailMap.get(teamList.get(teamIndex));
        for (int i = 0; i < numberOfTeams(); i++) {
            if (i == teamIndex) continue;
            Team otherTeam = teamToDetailMap.get(teamList.get(i));
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
        return teamToDetailMap.keySet();
    }

    /**
     * number of wins for given team
     *
     * @param team
     * @return
     */
    public int wins(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToDetailMap.get(team).win;
    }

    /**
     * number of losses for given team
     *
     * @param team
     * @return
     */
    public int losses(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToDetailMap.get(team).loss;
    }

    /**
     * number of remaining games for given team
     *
     * @param team
     * @return
     */
    public int remaining(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToDetailMap.get(team).remain;
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
        for (int i = 0; i < teamList.size(); i++) {
            if (teamList.get(i).equals(team2))
                return teamToDetailMap.get(team1).game[i];
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
        return teamToDetailMap.get(team).isEliminated;
    }

    /**
     * subset R of teams that eliminates given team; null if not eliminated
     *
     * @param team
     * @return
     */
    public Iterable<String> certificateOfElimination(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException();
        return teamToDetailMap.get(team).certificateOfElimination;
    }

    private boolean isValidTeam(String team) {
        if (team == null || teamToDetailMap.get(team) == null)
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
