package com.project.cricket.controller;

import com.project.cricket.requestbody.SeriesRequestBody;
import com.project.cricket.entity.Series;
import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.exception.ScoreCardNotFoundException;
import com.project.cricket.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @GetMapping("/")
    public ResponseEntity<String> getSeries(String seriesId) throws InvalidIdException,ScoreCardNotFoundException{
        return new ResponseEntity<>(seriesService.getSeriesResult(seriesId),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> createNewSeries(@RequestBody SeriesRequestBody seriesRequestBody) throws InvalidIdException{
        Series series = seriesService.createSeries(seriesRequestBody.getTeam1Name(), seriesRequestBody.getTeam2Name(), seriesRequestBody.getSeriesType(),seriesRequestBody.getNoOfMatches());
        return new ResponseEntity<>("Series initialized with seriesID: "+series.getSeriesId(),HttpStatus.OK);
    }

    @GetMapping("/play")
    public ResponseEntity<String> playSeries(@RequestParam String seriesId) throws InvalidIdException, ScoreCardNotFoundException {
        return new ResponseEntity<>(seriesService.playSeries(seriesId),HttpStatus.OK);
    }
}
