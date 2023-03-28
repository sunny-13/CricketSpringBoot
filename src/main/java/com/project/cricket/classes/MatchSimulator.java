package com.project.cricket.classes;

import com.project.cricket.entity.BattingScoreCard;
import com.project.cricket.entity.BowlingScoreCard;
import com.project.cricket.entity.Match;
import com.project.cricket.interfaces.BallDecision;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Data
@Slf4j
public class MatchSimulator {
    @Autowired
    private BallDecisionT20 ballDecisionT20;
    @Autowired
    private BallDecisionODI ballDecisionODI;

    public BallDecision ballDecision;
    private int totalBallsEachInning;
    private BattingScoreCard currentBatting;
    private BowlingScoreCard currentBowling;
    private int strikePlayer;
    private int nonStrikePlayer;
    private int currentBowler;
    private List<Boolean> playersOut = new ArrayList<>(Collections.nCopies(11, false));

    private void setMatchSimulator(Match match){
        switch (match.getMatchType()){
            case T20 -> {
                ballDecision = ballDecisionT20;
                totalBallsEachInning = 120;
            }
            case ODI -> {
                ballDecision = ballDecisionODI;
                totalBallsEachInning = 300;
            }
        }
        strikePlayer = 0;
        nonStrikePlayer = 1;
        currentBowler = 10;

    }

    public List<ScoreCard> playInning1(Match match){
        setMatchSimulator(match);
        currentBatting = new BattingScoreCard(match.getMatchId(),match.getFirstBattingTeam());
        currentBowling = new BowlingScoreCard(match.getMatchId(),
                match.getFirstBattingTeam().equals(match.getTeams().get(0)) ? match.getTeams().get(1):match.getTeams().get(0));
        int currBalls = 1;
        while (currBalls <= totalBallsEachInning && currentBatting.getWicketsDown() < 10) {
            ballStatsChange();
            int ballResult = ballDecision.ballDecision(getStrikePlayer());
            if (ballResult == 7) wicketDown();
            else runScored(ballResult);
            if (currBalls % 6 == 0) {
                overFinished();
            }
            currBalls++;
        }
        return new ArrayList<>(Arrays.asList(currentBatting,currentBowling));
    }

    public List<ScoreCard> playInning2(Match match,BattingScoreCard firstInningBatting){
        setMatchSimulator(match);
        currentBowling = new BowlingScoreCard(match.getMatchId(),match.getFirstBattingTeam());
        currentBatting = new BattingScoreCard(match.getMatchId(),
                match.getFirstBattingTeam().equals(match.getTeams().get(0)) ? match.getTeams().get(1):match.getTeams().get(0));
        int currBalls = 1;
        while (currBalls <= totalBallsEachInning && currentBatting.getWicketsDown() < 10 && currentBatting.getTotalRuns()<= firstInningBatting.getTotalRuns()) {
            ballStatsChange();
            int ballResult = ballDecision.ballDecision(getStrikePlayer());
            if (ballResult == 7) wicketDown();
            else runScored(ballResult);
            if (currBalls % 6 == 0) {
                overFinished();
            }
            currBalls++;
        }
        return new ArrayList<>(Arrays.asList(currentBatting,currentBowling));
    }

    private void changeStrike() {
        int temp = getStrikePlayer();
        setStrikePlayer(getNonStrikePlayer());
        setNonStrikePlayer(temp);
    }

    private void runScored(int run) {
        System.out.println(run + " ");
        int strike = getStrikePlayer();
        currentBatting.getRunsScored().set(strike, currentBatting.getRunsScored().get(strike) + run);
        currentBatting.setTotalRuns(currentBatting.getTotalRuns() + run);
        currentBowling.getRunsGiven().set(currentBowler,currentBowling.getRunsGiven().get(currentBowler)+run);
        if (run % 2 == 1) changeStrike();
    }

    private void ballStatsChange(){
        currentBatting.getBallsPlayed().set(strikePlayer,currentBatting.getBallsPlayed().get(strikePlayer)+1);
        currentBowling.getBallsBowled().set(currentBowler,currentBowling.getBallsBowled().get(currentBowler)+1);
    }

    private void wicketDown() {
        currentBatting.setWicketsDown(currentBatting.getWicketsDown() + 1);
        currentBowling.getWicketsTaken().set(currentBowler,currentBowling.getWicketsTaken().get(currentBowler)+1);
        List<Boolean> playerOutList = getPlayersOut();
        playerOutList.set(getStrikePlayer(), true);
        System.out.println("W ");
        for (int i = 0; i <= 10; i++) {
            if (!playerOutList.get(i)) {
                setStrikePlayer(i);
                playerOutList.set(i, true);
                break;
            }
        }
    }


    private void overFinished() {
        changeStrike();
        currentBowler = (currentBowler%5)+6;
    }


}
