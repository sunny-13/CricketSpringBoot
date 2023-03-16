package com.project.cricket.controller;

import com.project.cricket.requestbody.SeriesRequestBody;
import com.project.cricket.entity.Series;
import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.exception.ScoreCardNotFoundException;
import com.project.cricket.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @GetMapping("/")
    public String getSeries(String seriesId) throws InvalidIdException,ScoreCardNotFoundException{
        return seriesService.getSeriesResult(seriesId);
    }

    @PostMapping("/")
    public String createNewSeries(@RequestBody SeriesRequestBody seriesRequestBody) throws InvalidIdException{
        System.out.println("APi hitted");
        Series series = seriesService.createSeries(seriesRequestBody.getTeam1Name(), seriesRequestBody.getTeam2Name(), seriesRequestBody.getSeriesType(),seriesRequestBody.getNoOfMatches());
        return "Series initialized with seriesID: "+series.getSeriesId();
    }

    @GetMapping("/play")
    public String playSeries(@RequestParam String seriesId) throws InvalidIdException, ScoreCardNotFoundException {
        return seriesService.playSeries(seriesId);
    }
}
