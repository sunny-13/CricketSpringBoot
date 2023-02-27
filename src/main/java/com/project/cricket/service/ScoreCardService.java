package com.project.cricket.service;

import com.project.cricket.entity.ScoreCard;
import com.project.cricket.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreCardService {
    @Autowired
    private ScoreCardRepository scoreCardRepository;


    public ScoreCard addScoreCard(ScoreCard scoreCard){
        scoreCard.setScoreCardId(UUID.randomUUID().toString());
        return scoreCardRepository.save(scoreCard);
    }


    //GET SCORECARD BY ID Or MATCH-ID Or MATCH AND TEAM IDs.

    public ScoreCard getScoreCardById(String scoreCardId){
        Optional<ScoreCard> scoreCard = scoreCardRepository.findById(scoreCardId);
        if(scoreCard.isPresent()) return scoreCard.get();
        return null;

    }

    public List<ScoreCard> getScoreCardsByMatchId(String matchId){
        return scoreCardRepository.findByMatchId(matchId);
    }

    public ScoreCard getScoreCardByTeamIdMatchId(String teamId,String matchId){
        return scoreCardRepository.findByTeamIdMatchId(teamId,matchId);
    }


    //GET SCORECARS LIST BY TEAM-NAME OR TEAM-ID

    public List<ScoreCard> getScoreCardsByTeamId(String teamId){
        return scoreCardRepository.findByTeamId(teamId);
    }
    public List<ScoreCard> getScoreCardsByTeamName(String teamName){
        return scoreCardRepository.findByTeamName(teamName);
    }




}
