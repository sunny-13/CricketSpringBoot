package com.project.cricket.classes;

import com.project.cricket.interfaces.BallDecision;
import org.springframework.stereotype.Component;

@Component
public class BallDecisionODI implements BallDecision {
    public int ballDecisionBatsman(){

        int random = (int) (Math.random()*100)+1;
        if(random<97) return random%7;
        return 7;
    }



    public int ballDecisionBowler(){
        int random = (int) (Math.random()*100)+1;
        if(random<92) return random%7;
        return 7;
    }
}
