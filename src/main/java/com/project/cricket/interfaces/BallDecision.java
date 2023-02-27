package com.project.cricket.interfaces;

public interface BallDecision {
    default int ballDecision(int playerNum){
        if(playerNum<6) return ballDecisionBatsman();
        return ballDecisionBowler();
    }

    public int ballDecisionBatsman();
    public int ballDecisionBowler();
}
