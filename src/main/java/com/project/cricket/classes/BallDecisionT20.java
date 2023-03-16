package com.project.cricket.classes;

import com.project.cricket.interfaces.BallDecision;
import org.springframework.stereotype.Component;

@Component
public class BallDecisionT20 implements BallDecision {
    private final static int RANDOM_RANGE = 100;
    public int ballDecisionBatsman(){
        int random = (int) (Math.random()*RANDOM_RANGE)+1;
        if(random<90){
            if(random<80) return random%4;
            return random%2==0? 6:4;
        };
        return 7;
    }



    public int ballDecisionBowler(){
        int random = (int) (Math.random()*RANDOM_RANGE)+1;
        if(random<85){
            if(random<80) return random%4;
            return random%2==0? 6:4;

        }
        return 7;
    }
}
