package rankingSystem;

import java.util.List;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * @author jinli
 */
public class Matchup {

   
    // calculate the win probability of team one when it meets with team two.
    public static double probabilityWinner(Team one, Team two) {
        double totalMean = one.mean - two.mean;
        double totalDev = Math.sqrt((one.dev) * (one.dev) + (two.dev) * (two.dev));
        NormalDistribution nd = new NormalDistribution(totalMean, totalDev);

        return 1.0 - nd.cumulativeProbability(0.0);
    }

    public static double probabilityWinner(String one, String two, Ranking ranking) {

        Team first = new Team();
        Team second = new Team();
        List<Team> teams = ranking.getTeams();
        for(Team t : teams){
            String name = t.getName();
            if(name.equals(one)){
                first = t;
            }
            else if(name.equals(two)){
                second = t;
            }     
        }
        return probabilityWinner(first,second);
    }
}