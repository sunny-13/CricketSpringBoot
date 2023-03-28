package com.project.cricket.service;

import com.project.cricket.classes.SeriesStatus;
import com.project.cricket.entity.Series;
import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.exception.ScoreCardNotFoundException;
import com.project.cricket.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SeriesService {
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private MatchService matchService;

    public Series createSeries(String team1Name, String team2Name, String seriesType, int noOfMatches) throws InvalidIdException{
        List<String> matchIds = matchService.seriesMatchIds(seriesType,team1Name,team2Name,noOfMatches);
        Series series = new Series(UUID.randomUUID().toString(), Arrays.asList(team1Name,team2Name),noOfMatches,matchIds);
        seriesRepository.save(series);
        return series;
    }

    public Series getSeriesById(String seriesId) throws InvalidIdException {
        Optional<Series> series = seriesRepository.findById(seriesId);
        if(!series.isPresent()) throw new InvalidIdException("No series with id: "+seriesId+" found");
        return series.get();
    }

    public String playSeries(String seriesId) throws InvalidIdException, ScoreCardNotFoundException {
        Series series = getSeriesById(seriesId);
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
        else series.setWinnerTeam("TIE");
        series.setSeriesStatus(SeriesStatus.OVER);
        seriesRepository.save(series);
        return seriesMatchesResult;
    }

    public String getSeriesResult(String seriesId) throws InvalidIdException,ScoreCardNotFoundException{
        Series series = getSeriesById(seriesId);
        SeriesStatus seriesStatus = series.getSeriesStatus();
        if(!seriesStatus.equals(SeriesStatus.OVER)) return "Series not yet completed";
        String result = "";
        result="The teams in this series are: "+ series.getTeams().get(0)+" and  "+ series.getTeams().get(1)+"\n"
                +"The number of matches in series are: "+series.getNoOfMatches()+"\n";
        for(int i=0;i<series.getNoOfMatches();i++){
            result+= matchService.declareResult(series.getMatchIds().get(i));
            result+="\n";
        }
        return result;
    }
}
