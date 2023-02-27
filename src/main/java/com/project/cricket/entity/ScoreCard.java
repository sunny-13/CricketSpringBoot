package com.project.cricket.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document(collection = "ScoreCard")
@Data
public class ScoreCard {

    @Id
    private String scoreCardId;
    private String matchId;
    private String teamId;
    private String teamName;
    private int totalRuns;
    private int wicketsDown;
    private List<Integer> runsScored= new ArrayList<>(Collections.nCopies(11,0));

    private boolean win;

    public ScoreCard(String matchId,String teamId,String teamName){
        this.matchId = matchId;
        this.teamId = teamId;
        this.teamName=teamName;
    }
}
