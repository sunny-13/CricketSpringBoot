package com.project.cricket.entity;

import com.project.cricket.classes.MatchType;
import com.project.cricket.interfaces.BallDecision;
import com.project.cricket.classes.BallDecisionODI;
import com.project.cricket.classes.BallDecisionT20;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@Data
@Slf4j
public class Match {
    private String matchId;
    private MatchType matchType;
    public BallDecision ballDecision;
    private int totalBallsEachInning;

    private ScoreCard[] scoreCard;
    private ScoreCard currentBatting;
    private ScoreCard currentBowling;
    private int strikePlayer;
    private int nonStrikePlayer;
    private List<Boolean> playersOut = new ArrayList<>(Collections.nCopies(11,false));
    final private Logger logger = LoggerFactory.getLogger(Match.class);

    public String setMatchDetails(String matchType,String team1Name, String team2Name,String team1Id, String team2Id){
        try{

            setMatchId(UUID.randomUUID().toString());

            if(matchType.equals("ODI")){
                this.matchType = MatchType.ODI;
                totalBallsEachInning=300;
                ballDecision = new BallDecisionODI();
            }
            else{
                this.matchType = MatchType.T20;
                totalBallsEachInning=120;
                ballDecision = new BallDecisionT20();
            }
            scoreCard = new ScoreCard[2];
            scoreCard[0] = new ScoreCard(matchId,team1Id,team1Name);
            scoreCard[1] = new ScoreCard(matchId,team2Id,team2Name);
            return "Match Started!";
        }catch (Exception e){

            return "Some unknown error;";
        }
    }


    public String toss(String tossChoice){
        int random = (int) (Math.random()*2)+1;
        if((tossChoice.equals("HEADS") && random ==1) || (tossChoice.equals("TAILS") && random==2)){
            setCurrentBatting(scoreCard[0]);
            setCurrentBowling(scoreCard[1]);
        }
        else{
            setCurrentBatting(scoreCard[1]);
            setCurrentBowling(scoreCard[0]);
        }
        return currentBatting.getTeamName()+ "won the toss !";
    }

    private void changeStrike(){
        int temp = getStrikePlayer();
        setStrikePlayer(getNonStrikePlayer());
        setNonStrikePlayer(temp);
    }
    private void runScored(int run){
        logger.info(run+" ");
        int strike = getStrikePlayer();
        currentBatting.getRunsScored().set(strike,currentBatting.getRunsScored().get(strike)+run);
        currentBatting.setTotalRuns(currentBatting.getTotalRuns()+run);
        if(run%2==1) changeStrike();
    }

    private void wicketDown(){
        currentBatting.setWicketsDown(currentBatting.getWicketsDown()+1);
        List<Boolean> playerOutList = getPlayersOut();
        playerOutList.set(getStrikePlayer(),true);
        logger.info("W ");
        for(int i=0;i<=10;i++){
            if(!playerOutList.get(i)){
                setStrikePlayer(i);
                playerOutList.set(i,true);
                break;
            }
        }
    }


    private void overFinished(){
//        logger.info("\n");
        changeStrike();
    }




    public ScoreCard playInning1(){
        int currBalls = 1;
        while (currBalls <= totalBallsEachInning && currentBatting.getWicketsDown() < 10) {
            int ballResult = ballDecision.ballDecision(getStrikePlayer());
            if(ballResult==7) wicketDown();
            else runScored(ballResult);

            if (currBalls % 6 == 0) {
                overFinished();
            }
            currBalls++;
        }
        changeInning();
        return currentBowling;
    }

    private void changeInning(){
        for(int i=0;i<11;i++) {
            playersOut.set(i, false);
        }
        ScoreCard temp = currentBatting;
        currentBatting = currentBowling;
        currentBowling =temp;
    }

    public ScoreCard playInning2(){

        int currBalls = 1;
        while (currBalls <= totalBallsEachInning && currentBatting.getWicketsDown() < 10 && currentBatting.getTotalRuns() <= currentBowling.getTotalRuns()) {

            int ballResult = ballDecision.ballDecision(getStrikePlayer());
            if(ballResult==7) wicketDown();
            else runScored(ballResult);

            if (currBalls % 6 == 0) {
                overFinished();
            }
            currBalls++;
        }
        return currentBatting;
    }

    public String declareResult(){
        logger.info("\n");
        if(getScoreCard0().getTotalRuns()>getScoreCard1().getTotalRuns()) {
            getScoreCard0().setWin(true);
            return getScoreCard0().getTeamName()+" wins";
        }
        else if(getScoreCard0().getTotalRuns()<getScoreCard1().getTotalRuns()) {
            getScoreCard1().setWin(true);
            return getScoreCard1().getTeamName()+" wins";
        }
        return "TIE";
    }

    public ScoreCard getScoreCard0(){
        return scoreCard[0];
    }

    public ScoreCard getScoreCard1(){
        return scoreCard[1];
    }

}
