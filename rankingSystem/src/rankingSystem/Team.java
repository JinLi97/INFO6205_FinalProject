package rankingSystem;

import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;

public class Team implements Comparable<Team> {

    private String name;
    private int win;
    private int lose;
    private int rankValue;
    
    public int dataNum;
    public double mean;
    public double dev;
    public double s;
    public double rank;

    public NormalDistribution normalDistribution;
    
    public Team(){
        
    }

    public Team(String name) {
        this.name = name;
    }

    public int getRankValue() {
        return rankValue;
    }

    public void setRankValue(int rankValue) {
        this.rankValue = rankValue;
    }
   

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getWin(){
        return win;
    }
    
    public void setWin(int win){
        this.win = win;
    }
    
    public int getLose(){
        return Math.abs(lose);
    }
    
    public void setLose(int lose){
        this.lose = lose;
    }

    public void updateDistribution(String line) {
        String[] fields = line.split(",");
        if (name.equals(fields[2])) {
            if (Integer.parseInt(fields[3]) > Integer.parseInt(fields[5])) {
                win++;
                addDataValue(1.0);
            } else if (Integer.parseInt(fields[3]) < Integer.parseInt(fields[5])) {
                addDataValue(0.0);
            } else {
                lose--;
                addDataValue(-1.0);
            }
        } else {
            if (Integer.parseInt(fields[3]) < Integer.parseInt(fields[5])) {
                win++;
                addDataValue(1.0);
            } else if (fields[3].equals("0")) {
                addDataValue(0.0);
            } else {
                lose--;
                addDataValue(-1.0);
            }
        }

    }

    public void addDataValue(double x) {
        //mean = (mean * dataNum + x) / (dataNum + 1);
        dataNum++;
        s = s + 1.0 * (dataNum - 1) / dataNum * (x - mean) * (x - mean);
        mean = mean + (x - mean) / dataNum;

        dev = Math.sqrt(s / (dataNum - 1));

//		mean += x;
//		dataNum++;
    }

    public void calRank(List<Team> teams) {
        for (Team t : teams) {
            if (t.name.equals(this.name)) {
                continue;
            }
            rank += Matchup.probabilityWinner(this, t);
        }
    }

    public void updateNomalDis() {
        this.normalDistribution = new NormalDistribution(mean, dev);
    }

    @Override
    public int compareTo(Team o) {

        return Double.compare(this.rank, o.rank);
    }

}
