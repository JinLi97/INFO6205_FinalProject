package rankingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking {

    private ArrayList<Team> teams; // list of teams in NBA

    public Ranking() {
        teams = new ArrayList<>();
        readTeamFromCsv();			//read team.
        readFromCsv();				//read matchup data
        calculateTeamRank();		//calculate team win probability
        Collections.sort(teams, Collections.reverseOrder()); //sort list by team win probability.
        updateRankValue();			//update the rank value
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    //read teams names from "team.csv"
    public void readTeamFromCsv() {
        String data = "team.csv";
        try ( BufferedReader br = new BufferedReader(new FileReader(data));) {
            String line = null;
            while ((line = br.readLine()) != null) {
                Team newTeam = new Team(line);
                teams.add(newTeam);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //read matchup data from "data.csv" or "19-data.csv".
    public void readFromCsv() {
        String data = "19-20data.csv";
        try ( BufferedReader br = new BufferedReader(new FileReader(data));) {
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                for (Team t : teams) {
                    String teamName = t.getName();
                    if (teamName.equals(fields[2]) || teamName.equals(fields[4])) {
                        t.updateDistribution(line);
                    }
                }
            }
            for (Team t : teams) {
                t.updateNomalDis();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //calculate the team win probability.
    private void calculateTeamRank() {
        for (Team t : teams) {
            t.calRank(teams);
        }

    }

    //update the team rank value.
    private void updateRankValue() {
        for (Team t : teams) {
            t.setRankValue(teams.indexOf(t) + 1);
        }
    }

}
