package com.project.cricket.controller;

import com.project.cricket.entity.ScoreCard;
import com.project.cricket.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping("/new")
    public String startMatch(@RequestParam String matchType,@RequestParam String team1Name,@RequestParam String team2Name){
        return matchService.setMatchDetails(matchType,team1Name,team2Name);

    }

    @PostMapping("/toss")
    public String tossMatch(@RequestParam String tossChoice){
        return matchService.toss(tossChoice);
    }

    @GetMapping("/playInning1")
    public ScoreCard playInning1(){
        return matchService.playInning1();
    }

    @GetMapping("/playInning2")
    public ScoreCard playInning2(){
        return matchService.playInning2();
    }

    @GetMapping("/getResult")
    public String getResult(){
        return matchService.declareResult();
    }

    @PostMapping("/saveResults")
    public String saveResults(){
        return matchService.saveResults();
    }
}
