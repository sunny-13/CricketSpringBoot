package com.project.cricket.service;

import com.project.cricket.classes.ScoreCard;
import com.project.cricket.entity.BattingScoreCard;
import com.project.cricket.entity.BowlingScoreCard;
import com.project.cricket.repository.BattingScoreCardRepository;
import com.project.cricket.repository.BowlingScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class ScoreCardService {
    @Autowired
    private BattingScoreCardRepository battingScoreCardRepository;
    @Autowired
    private BowlingScoreCardRepository bowlingScoreCardRepository;

    public BattingScoreCard findBattingByMatchIdTeamName(String matchId, String teamName){
        BattingScoreCard battingScoreCard = battingScoreCardRepository.findByMatchIdTeamName(matchId,teamName);
        if(isNull(battingScoreCard)) return null;
        return battingScoreCard;
    }

    public BowlingScoreCard findBowlingByMatchIdTeamName(String matchId, String teamName){
        BowlingScoreCard bowlingScoreCard = bowlingScoreCardRepository.findByMatchIdTeamName(matchId,teamName);
        if(isNull(bowlingScoreCard)) return null;
        return bowlingScoreCard;
    }


    public void saveBattingScoreCard(BattingScoreCard battingScoreCard){
        battingScoreCardRepository.save(battingScoreCard);
    }
    public void saveBowlingScoreCard(BowlingScoreCard bowlingScoreCard){
        bowlingScoreCardRepository.save(bowlingScoreCard);
    }

    public List<ScoreCard> findByMatchId(String matchId){
        List<ScoreCard> scoreCards = new ArrayList<>();
        List<BattingScoreCard> battingScoreCards = battingScoreCardRepository.findByMatchId(matchId);
        List<BowlingScoreCard> bowlingScoreCards = bowlingScoreCardRepository.findByMatchId(matchId);

        for(BattingScoreCard battingScoreCard: battingScoreCards) scoreCards.add(battingScoreCard);
        for(BowlingScoreCard bowlingScoreCard: bowlingScoreCards) scoreCards.add(bowlingScoreCard);
        return scoreCards;
    }




}
