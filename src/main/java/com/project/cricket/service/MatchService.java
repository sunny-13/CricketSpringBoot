package com.project.cricket.service;

import com.project.cricket.classes.*;
import com.project.cricket.entity.BattingScoreCard;
import com.project.cricket.entity.BowlingScoreCard;
import com.project.cricket.entity.Match;
import com.project.cricket.entity.Player;
import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.exception.ScoreCardNotFoundException;
import com.project.cricket.repository.BattingScoreCardRepository;
import com.project.cricket.repository.BowlingScoreCardRepository;
import com.project.cricket.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Objects.isNull;


@Service
public class MatchService {
    @Autowired
    private MatchSimulator matchSimulator;
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ScoreCardService scoreCardService;
    @Autowired
    private TeamService teamService;

    public String newMatch(String matchType,String team1Name, String team2Name) throws InvalidIdException{
        Match match = new Match(matchType,team1Name,team2Name);
        teamService.updateMatchList(team1Name,match.getMatchId());
        teamService.updateMatchList(team2Name,match.getMatchId());
        matchRepository.save(match);
        return "Match created with id: "+ match.getMatchId();
    }
    public Match getMatchById (String matchId) throws InvalidIdException{
        Optional<Match> matchOptional= matchRepository.findById(matchId);
        if(!matchOptional.isPresent()) throw new InvalidIdException("Match not found id: "+matchId+ " found");
        return matchOptional.get();
    }



    public String toss(String matchId, String tossChoice) throws InvalidIdException{
        Match match = getMatchById(matchId);
        if(match.getStatus()!= MatchStatus.INITIALIZED) return "Toss already done!";
        int random = (int) (Math.random() * 2) + 1;
        if ((tossChoice.equals("HEADS") && random == 1) || (tossChoice.equals("TAILS") && random == 2)) {
            match.setFirstBattingTeam(match.getTeams().get(0));
        } else {
            match.setFirstBattingTeam(match.getTeams().get(1));
        }
        match.setStatus(MatchStatus.TOSS_DONE);
        matchRepository.save(match);
        return match.getFirstBattingTeam() + " won the toss !";
    }

    public List<ScoreCard> playInning1(String matchId) throws InvalidIdException ,ScoreCardNotFoundException{
        Match match = getMatchById(matchId);
        MatchStatus matchStatus = match.getStatus();
        switch (matchStatus) {
            case INITIALIZED -> {
                System.out.println("Toss yet not done");
                return null;
            }
            case TOSS_DONE -> {
                List<ScoreCard> scoreCards = matchSimulator.playInning1(match);
                match.setStatus(MatchStatus.INNING1_OVER);
                matchRepository.save(match);
                scoreCardService.saveBattingScoreCard((BattingScoreCard) scoreCards.get(0));
                scoreCardService.saveBowlingScoreCard((BowlingScoreCard) scoreCards.get(1));
                return scoreCards;
            }
            default -> {
                System.out.println("Inning1 Already Over");
                List<ScoreCard> scoreCards = new ArrayList<>();
                scoreCards.add(scoreCardService.findBattingByMatchIdTeamName(matchId,match.getFirstBattingTeam()));
                scoreCards.add(scoreCardService.findBowlingByMatchIdTeamName(matchId,
                        match.getFirstBattingTeam().equals(match.getTeams().get(0)) ? match.getTeams().get(1):match.getTeams().get(0)));
                return scoreCards;
            }
        }

    }

    public List<ScoreCard> playInning2(String matchId) throws InvalidIdException, ScoreCardNotFoundException {
        Match match = getMatchById(matchId);
        MatchStatus matchStatus = match.getStatus();
        switch (matchStatus) {
            case INITIALIZED -> {
                System.out.println("Toss yet not done !");
                return null;
            }
            case TOSS_DONE -> {
                System.out.println("Inning1 not done !");
                return null;
            }
            case INNING1_OVER -> {
                BattingScoreCard firstInningBatting = scoreCardService.findBattingByMatchIdTeamName(matchId,match.getFirstBattingTeam());
                List<ScoreCard> scoreCards = matchSimulator.playInning2(match,firstInningBatting);
                match.setStatus(MatchStatus.OVER);
                matchRepository.save(match);
                scoreCardService.saveBattingScoreCard((BattingScoreCard) scoreCards.get(0));
                scoreCardService.saveBowlingScoreCard((BowlingScoreCard) scoreCards.get(1));
                return scoreCards;
            }
            default -> {
                System.out.println("Inning2 Already Over");
                List<ScoreCard> scoreCards = scoreCardService.findByMatchId(matchId);
                return scoreCards;
            }
        }

    }


    public String declareResult(String matchId) throws InvalidIdException,ScoreCardNotFoundException{
        Match match = getMatchById(matchId);
        switch (match.getStatus()) {
            case OVER -> {
                break;
            }
            default -> {
                return "Match Not Completed Yet !";
            }
        }
        String result="";
        BattingScoreCard firstInningBatting = scoreCardService.findBattingByMatchIdTeamName(matchId,match.getFirstBattingTeam());
        BattingScoreCard secondInningBatting = scoreCardService.findBattingByMatchIdTeamName(matchId,
                match.getFirstBattingTeam().equals(match.getTeams().get(0)) ? match.getTeams().get(1):match.getTeams().get(0));
        result+=firstInningBatting.getTeamName()+" : "+firstInningBatting.getTotalRuns()+"/"+firstInningBatting.getWicketsDown()+"\n";
        result+=secondInningBatting.getTeamName()+" : "+secondInningBatting.getTotalRuns()+"/"+secondInningBatting.getWicketsDown()+"\n";
        if(firstInningBatting.getTotalRuns()> secondInningBatting.getTotalRuns()) {
            match.setWinnerTeam(firstInningBatting.getTeamName());
            matchRepository.save(match);
            result+= firstInningBatting.getTeamName() + " wins! \n";
        }

        else if(firstInningBatting.getTotalRuns()< secondInningBatting.getTotalRuns()){
            match.setWinnerTeam(secondInningBatting.getTeamName());
            matchRepository.save(match);
            result+= secondInningBatting.getTeamName() + " wins! \n";
        }
        else result+= " Match Tie! \n";
        return result;

    }



    public String getWinnerTeam(String matchId) throws InvalidIdException{
        Match match = getMatchById(matchId);
        return match.getWinnerTeam();
    }

    public List<String> seriesMatchIds(String matchType,String team1Name, String team2Name,int noOfMatches) throws InvalidIdException{
        List<String> matchIds = new ArrayList<>();
        Match match;
        for(int i=0;i<noOfMatches;i++){
            match = new Match(matchType,team1Name,team2Name);
            teamService.updateMatchList(team1Name,match.getMatchId());
            teamService.updateMatchList(team2Name,match.getMatchId());
            matchRepository.save(match);
            matchIds.add(match.getMatchId());
        }
        return matchIds;
    }

    public String playSeriesMatches(String matchId) throws InvalidIdException,ScoreCardNotFoundException {
        toss(matchId,(int) (Math.random() * 2) + 1==1 ? "HEADS":"TAILS");
        playInning1(matchId);
        playInning2(matchId);
        String result = declareResult(matchId);
        return result;
    }

    public MatchStatus getStatus(String matchId) throws InvalidIdException{
        Match match = getMatchById(matchId);
        return match.getStatus();
    }

    public List<String> getMatchFormattedScoreCard(String matchId) throws InvalidIdException,ScoreCardNotFoundException{
        Match match = getMatchById(matchId);
        List<ScoreCard> scoreCards = scoreCardService.findByMatchId(matchId);
        List<String> formattedScorecard = new ArrayList<>();

        for(ScoreCard scoreCard : scoreCards){
            if(scoreCard.getScoreCardType().equals(ScoreCardType.BATTING_SCORECARD)){

                BattingScoreCard scoreCardCasted = (BattingScoreCard) scoreCard;
                String teamScore = scoreCardCasted.getTotalRuns()+"/"+scoreCardCasted.getWicketsDown();
                formattedScorecard.add("BattingScorecard: "+scoreCardCasted.getTeamName()+" : "+teamScore);
                List<String> playerNames = teamService.getPlayerNamesByTeamName(scoreCard.getTeamName());
                for(int i=0;i<11;i++){
                    String playerScore = scoreCardCasted.getRunsScored().get(i)+"/"+scoreCardCasted.getBallsPlayed().get(i);
                    formattedScorecard.add(playerNames.get(i)+" : "+playerScore);
                }
            }
            else{
                BowlingScoreCard scoreCardCasted = (BowlingScoreCard) scoreCard;
                formattedScorecard.add("BowlingScorecard"+" : "+scoreCardCasted.getTeamName());
                List<String> playerNames = teamService.getPlayerNamesByTeamName(scoreCard.getTeamName());
                for(int i=0;i<11;i++){
                    if(scoreCardCasted.getBallsBowled().get(i)==0) continue;
                    String playerBowlingStats = scoreCardCasted.getRunsGiven().get(i)+"/"+scoreCardCasted.getBallsBowled().get(i);
                    formattedScorecard.add(playerNames.get(i)+" : "+playerBowlingStats);
                }
            }
        }

        formattedScorecard.add(match.getWinnerTeam()+" wins!");
        return formattedScorecard;
    }

}
