package com.project.cricket.entity;

import com.project.cricket.classes.ScoreCard;
import com.project.cricket.classes.ScoreCardType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Document(collection = "BattingScorecard")
@Data
public class BattingScoreCard extends ScoreCard {
    @Id
    private String scoreCardId;
    private int totalRuns;
    private int wicketsDown;
    private List<Integer> runsScored;
    private List<Integer> ballsPlayed;

    public BattingScoreCard(String matchId,String teamName){
        setScoreCardId(UUID.randomUUID().toString());
        setMatchId(matchId);
        setTeamName(teamName);
        runsScored = new ArrayList<>(Collections.nCopies(11,0));
        ballsPlayed = new ArrayList<>(Collections.nCopies(11,0));
        setScoreCardType(ScoreCardType.BATTING_SCORECARD);
    }
}
