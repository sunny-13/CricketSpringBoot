package com.project.cricket.service;

import com.project.cricket.classes.ScoreCard;
import com.project.cricket.classes.SeriesStatus;
import com.project.cricket.entity.Series;
import com.project.cricket.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class SeriesService {
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private MatchService matchService;

    public Series createSeries(String team1Name, String team2Name, String seriesType, int noOfMatches){
        List<String> matchIds = matchService.seriesMatchIds(seriesType,team1Name,team2Name,noOfMatches);
        Series series = new Series(UUID.randomUUID().toString(), Arrays.asList(team1Name,team2Name),noOfMatches,matchIds);
        seriesRepository.save(series);
        return series;
    }

    public String playSeries(String seriesId){
        Series series = seriesRepository.findById(seriesId).get();
        if(isNull(series)) {
            System.out.println("No series found with given ID");
            return null;
        }
        String seriesMatchesResult ="";
        int team1wins=0,team2wins=0;
        for(int i=0;i<series.getNoOfMatches();i++){
            seriesMatchesResult+=(matchService.playSeriesMatches(series.getMatchIds().get(i)));
            if(matchService.getWinnerTeam(series.getMatchIds().get(i)).equals(series.getTeams().get(0)))
                team1wins++;
            else if(matchService.getWinnerTeam(series.getMatchIds().get(i)).equals(series.getTeams().get(1)))
                team2wins++;
        };
        if(team1wins>team2wins) {
            series.setWinnerTeam(series.getTeams().get(0));
            seriesMatchesResult += series.getTeams().get(0)+" wins the series with "+team1wins+" wins! \n";
        }
        else if(team1wins<team2wins) {
            series.setWinnerTeam(series.getTeams().get(1));
            seriesMatchesResult += series.getTeams().get(1)+" wins the series with "+team2wins+" wins! \n";
        }
        series.setSeriesStatus(SeriesStatus.OVER);
        seriesRepository.save(series);
        return seriesMatchesResult;
    }
}
