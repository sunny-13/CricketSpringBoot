package com.project.cricket.service;

import com.project.cricket.entity.Match;
import com.project.cricket.entity.ScoreCard;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Data
@NoArgsConstructor
public class MatchService {
    @Autowired
    private Match match;
    @Autowired
    private TeamService teamService;
    @Autowired
    private ScoreCardService scoreCardService;
    @Autowired
    private PlayerService playerService;

    public String setMatchDetails(String matchType,String team1Name, String team2Name){

        String team1Id = teamService.getTeamIdByName(team1Name);
        String team2Id = teamService.getTeamIdByName(team2Name);
        return match.setMatchDetails(matchType,team1Name,team2Name,team1Id,team2Id);
    }

    public String toss(String tossChoice){
        return match.toss(tossChoice);
    }


    public ScoreCard playInning1(){
        return match.playInning1();
    }

    public ScoreCard playInning2(){
        return match.playInning2();
    }

    public String declareResult(){
        return match.declareResult();
    }

    public String saveResults(){

        try{
            addScoreCard();
            updateMatchListTeam();
            updateMatchListPlayer();
            return "Results persisted !";

        }catch (Exception e){
            e.printStackTrace();
            return "Results couldn't be persisted !";
        }
    }

    private void addScoreCard(){
        scoreCardService.addScoreCard(match.getScoreCard0());
        scoreCardService.addScoreCard(match.getScoreCard1());
    }

    public void updateMatchListTeam(){
        teamService.updateMatchList(match.getScoreCard0().getTeamId(),match.getMatchId());
        teamService.updateMatchList(match.getScoreCard1().getTeamId(),match.getMatchId());
    }

    public void updateMatchListPlayer(){
        playerService.updateMatchList(teamService.getPlayerListByTeamId(match.getScoreCard0().getTeamId()),match.getMatchId());
        playerService.updateMatchList(teamService.getPlayerListByTeamId(match.getScoreCard1().getTeamId()),match.getMatchId());
    }

}
