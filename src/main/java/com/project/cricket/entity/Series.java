package com.project.cricket.entity;

import com.project.cricket.classes.MatchType;
import com.project.cricket.classes.SeriesStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Series")
@Data
@NoArgsConstructor
public class Series {
    @Id
    private String seriesId;
    private List<String> teams;
    private int noOfMatches;
    private List<String> matchIds;
    private SeriesStatus seriesStatus;

    private String winnerTeam;

    public Series(String seriesId,List<String> teams, int noOfMatches, List<String> matchIds){
        this.seriesId = seriesId;
        this.teams = teams;
        this.noOfMatches = noOfMatches;
        this.matchIds = matchIds;
        seriesStatus = SeriesStatus.INITIALIZED;
    }



}
