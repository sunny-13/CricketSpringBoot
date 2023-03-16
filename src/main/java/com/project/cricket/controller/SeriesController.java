package com.project.cricket.controller;

import com.project.cricket.entity.Series;
import com.project.cricket.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @PostMapping("/")
    public String createNewSeries(@RequestParam String team1Name,@RequestParam String team2Name,@RequestParam String seriesType, @RequestParam int noOfMatches){
        Series series = seriesService.createSeries(team1Name, team2Name, seriesType, noOfMatches);
        return "Series initialized with seriesID: "+series.getSeriesId();
    }

    @GetMapping("/play")
    public String playSeries(@RequestParam String seriesId){
        return seriesService.playSeries(seriesId);
    }
}
