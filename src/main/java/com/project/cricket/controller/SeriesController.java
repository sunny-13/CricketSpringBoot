package com.project.cricket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="/series")
public class SeriesController {

    @PostMapping("/create")
    public String createNewSeries(@RequestParam String seriesType, @RequestParam int noOfMatches, @RequestParam String[] teams){

        return "In progress";
    }
}
