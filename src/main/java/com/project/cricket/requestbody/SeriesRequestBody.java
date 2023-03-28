package com.project.cricket.requestbody;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesRequestBody {
    private String team1Name;
    private String team2Name;
    private String seriesType;
    private int noOfMatches;
}
