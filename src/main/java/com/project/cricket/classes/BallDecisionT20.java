package com.project.cricket.classes;

import com.project.cricket.interfaces.BallDecision;
import org.springframework.stereotype.Component;

@Component
public class BallDecisionT20 implements BallDecision {
    private final static int RANDOM_MAX = 100;


    private final static int CUMULATIVE_ZERO_RUN_PROBABILITY_BATSMAN = 20;
    private final static int CUMULATIVE_ONE_RUN_PROBABILITY_BATSMAN = 45;
    private final static int CUMULATIVE_TWO_RUN_PROBABILITY_BATSMAN = 60;
    private final static int CUMULATIVE_THREE_RUN_PROBABILITY_BATSMAN = 70;
    private final static int CUMULATIVE_FOUR_RUN_PROBABILITY_BATSMAN = 82;
    private final static int CUMULATIVE_SIX_RUN_PROBABILITY_BATSMAN = 92;


    private final static int CUMULATIVE_ZERO_RUN_PROBABILITY_BOWLER = 15;
    private final static int CUMULATIVE_ONE_RUN_PROBABILITY_BOWLER = 35;
    private final static int CUMULATIVE_TWO_RUN_PROBABILITY_BOWLER = 50;
    private final static int CUMULATIVE_THREE_RUN_PROBABILITY_BOWLER = 60;
    private final static int CUMULATIVE_FOUR_RUN_PROBABILITY_BOWLER = 68;
    private final static int CUMULATIVE_SIX_RUN_PROBABILITY_BOWLER = 75;

    public int ballDecisionBatsman(){
        int random = (int) (Math.random()*RANDOM_MAX)+1;
        if(random<=CUMULATIVE_ZERO_RUN_PROBABILITY_BATSMAN) return 0;
        else if(random<=CUMULATIVE_ONE_RUN_PROBABILITY_BATSMAN) return 1;
        else if(random<=CUMULATIVE_TWO_RUN_PROBABILITY_BATSMAN) return 2;
        else if(random<=CUMULATIVE_THREE_RUN_PROBABILITY_BATSMAN) return 3;
        else if(random<=CUMULATIVE_FOUR_RUN_PROBABILITY_BATSMAN) return 4;
        else if(random<=CUMULATIVE_SIX_RUN_PROBABILITY_BATSMAN) return 6;
        return 7;


    }



    public int ballDecisionBowler(){
        int random = (int) (Math.random()*RANDOM_MAX)+1;

        if(random<=CUMULATIVE_ZERO_RUN_PROBABILITY_BOWLER) return 0;
        else if(random<=CUMULATIVE_ONE_RUN_PROBABILITY_BOWLER) return 1;
        else if(random<=CUMULATIVE_TWO_RUN_PROBABILITY_BOWLER) return 2;
        else if(random<=CUMULATIVE_THREE_RUN_PROBABILITY_BOWLER) return 3;
        else if(random<=CUMULATIVE_FOUR_RUN_PROBABILITY_BOWLER) return 4;
        else if(random<=CUMULATIVE_SIX_RUN_PROBABILITY_BOWLER) return 6;
        return 7;
    }
}
