package com.project.cricket.service;

import com.project.cricket.classes.MatchSimulator;
import com.project.cricket.classes.MatchStatus;
import com.project.cricket.classes.ScoreCard;
import com.project.cricket.entity.BattingScoreCard;
import com.project.cricket.entity.BowlingScoreCard;
import com.project.cricket.entity.Match;
import com.project.cricket.repository.BattingScoreCardRepository;
import com.project.cricket.repository.BowlingScoreCardRepository;
import com.project.cricket.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private BattingScoreCardRepository battingScoreCardRepository;
    @Autowired
    private BowlingScoreCardRepository bowlingScoreCardRepository;

    public String newMatch(String matchType,String team1Name, String team2Name){
        Match match = new Match(matchType,team1Name,team2Name);
        matchRepository.save(match);
        return "Match created with id: "+ match.getMatchId();
    }



    public String toss(String matchId, String tossChoice){
        Optional<?> matchOptional= matchRepository.findById(matchId);
        if(!matchOptional.isPresent()) return "No match found with given ID";
        Match match = ((Match) matchOptional.get());
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

    public List<ScoreCard> playInning1(String matchId){
        Match match = matchRepository.findById(matchId).get();
        if(isNull(match)) {
            System.out.println("No match found with given ID");
            return null;
        }
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
                scoreCards.add(battingScoreCardRepository.findByMatchIdTeamName(matchId,match.getFirstBattingTeam()));
                scoreCards.add(bowlingScoreCardRepository.findByMatchIdTeamName(matchId,
                        match.getFirstBattingTeam().equals(match.getTeams().get(0)) ? match.getTeams().get(1):match.getTeams().get(0)));
                return scoreCards;
            }
        }

    }

    public List<ScoreCard> playInning2(String matchId){
        Match match = matchRepository.findById(matchId).get();
        if(isNull(match)) {
            System.out.println("No match found with given ID");
            return null;
        }
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


    public String declareResult(String matchId) {
        Match match = matchRepository.findById(matchId).get();
        if(isNull(match)) {
            System.out.println("No match found with given ID");
            return null;
        }
        switch (match.getStatus()) {
            case OVER -> {
                break;
            }
            default -> {
                return "Match Not Completed Yet !";
            }
        }
        String result="";
        BattingScoreCard firstInningBatting = battingScoreCardRepository.findByMatchIdTeamName(matchId,match.getFirstBattingTeam());
        BattingScoreCard secondInningBatting = battingScoreCardRepository.findByMatchIdTeamName(matchId,
                match.getFirstBattingTeam().equals(match.getTeams().get(0)) ? match.getTeams().get(1):match.getTeams().get(0));
        result+=firstInningBatting.getTeamName()+" : "+firstInningBatting.getTotalRuns()+"/"+firstInningBatting.getWicketsDown()+"\n";
        result+=secondInningBatting.getTeamName()+" : "+secondInningBatting.getTotalRuns()+"/"+secondInningBatting.getWicketsDown()+"\n";
        if(firstInningBatting.getTotalRuns()> secondInningBatting.getTotalRuns()){
            match.setWinnerTeam(firstInningBatting.getTeamName());
            matchRepository.save(match);
            result+= firstInningBatting.getTeamName() + "wins! \n";
        }

        else if(firstInningBatting.getTotalRuns()< secondInningBatting.getTotalRuns()){
            match.setWinnerTeam(secondInningBatting.getTeamName());
            matchRepository.save(match);
            result+= secondInningBatting.getTeamName() + "wins! \n";
        }
        else result+= "Match Tie! \n";
        return result;

    }

    public MatchStatus getStatus(String matchId){
        Match match = matchRepository.findById(matchId).get();
        if(isNull(match)) {
            System.out.println("No match found with given ID");
            return null;
        }
        return match.getStatus();
    }

    public List<ScoreCard> getMatchScoreCards(String matchId){
        Match match = matchRepository.findById(matchId).get();
        if(isNull(match)) {
            System.out.println("No match found with given ID");
            return null;
        }
        return scoreCardService.findByMatchId(matchId);
    }

    public String getWinnerTeam(String matchId){
        Match match = matchRepository.findById(matchId).get();
        if(isNull(match)) {
            System.out.println("No match found with given ID");
            return null;
        }
        return match.getWinnerTeam();
    }

    public List<String> seriesMatchIds(String matchType,String team1Name, String team2Name,int noOfMatches){
        List<String> matchIds = new ArrayList<>();
        Match match;
        for(int i=0;i<noOfMatches;i++){
            match = new Match(matchType,team1Name,team2Name);
            matchRepository.save(match);
            matchIds.add(match.getMatchId());
        }
        return matchIds;
    }

    public String playSeriesMatches(String matchId){
        toss(matchId,(int) (Math.random() * 2) + 1==1 ? "HEADS":"TAILS");
        playInning1(matchId);
        playInning2(matchId);
        String result = declareResult(matchId);
        return result;
    }

}
