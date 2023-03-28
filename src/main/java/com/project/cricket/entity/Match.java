package com.project.cricket.entity;

import com.project.cricket.classes.MatchStatus;
import com.project.cricket.classes.MatchType;
import com.project.cricket.interfaces.BallDecision;
import com.project.cricket.classes.BallDecisionODI;
import com.project.cricket.classes.BallDecisionT20;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.*;

@Document(collection = "Match")
@Data
@NoArgsConstructor
public class Match {
    @Id
    private String matchId;
    private MatchType matchType;
    private Date date;
    private List<String> teams;
    private MatchStatus status;
    private String winnerTeam;
    private String firstBattingTeam;

    public Match(String matchType, String team1Name, String team2Name) {

        setMatchId(UUID.randomUUID().toString());
        setDate(new Date());
        teams = new ArrayList<>(Arrays.asList(team1Name,team2Name));
        if (matchType.equals("ODI")) {
            this.matchType = MatchType.ODI;
        } else {
            this.matchType = MatchType.T20;
        }
        status = MatchStatus.INITIALIZED;


    }


}
