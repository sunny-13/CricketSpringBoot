package com.project.cricket.controller;

import com.project.cricket.classes.ScoreCard;
import com.project.cricket.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping("/create")
    public String startMatch(@RequestParam String matchType,@RequestParam String team1Name,@RequestParam String team2Name){
        return matchService.newMatch(matchType, team1Name, team2Name);

    }

    @PostMapping("/toss")
    public String tossMatch(@RequestParam String matchId, @RequestParam String tossChoice){
        return matchService.toss(matchId,tossChoice);
    }

    @GetMapping("/playInning1")
    public List<ScoreCard> playInning1(@RequestParam String matchId){
        return matchService.playInning1(matchId);
    }

    @GetMapping("/playInning2")
    public List<ScoreCard> playInning2(@RequestParam String matchId){
        return matchService.playInning2(matchId);
    }

    @GetMapping("/result")
    public String getResult(@RequestParam String matchId){
        return matchService.declareResult(matchId);
    }

}
