package com.project.cricket.entity;

import com.project.cricket.classes.ScoreCard;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Document(collection = "BowlingScorecard")
@Data
@NoArgsConstructor
public class BowlingScoreCard extends ScoreCard {
    @Id
    private String scoreCardId;
    private List<Integer> ballsBowled;
    private List<Integer> runsGiven;
    private List<Integer> wicketsTaken;

    public BowlingScoreCard(String matchId,String teamName){
        setScoreCardId(UUID.randomUUID().toString());
        setMatchId(matchId);
        setTeamName(teamName);
        runsGiven = new ArrayList<>(Collections.nCopies(11,0));
        ballsBowled = new ArrayList<>(Collections.nCopies(11,0));
        wicketsTaken = new ArrayList<>(Collections.nCopies(11,0));
    }


}
