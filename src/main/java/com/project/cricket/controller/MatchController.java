package com.project.cricket.controller;

import com.project.cricket.requestbody.MatchRequestBody;
import com.project.cricket.classes.ScoreCard;

import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.exception.ScoreCardNotFoundException;
import com.project.cricket.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping("/")
    public String createMatch(@RequestBody MatchRequestBody matchRequestBody) throws InvalidIdException{
        return matchService.newMatch(matchRequestBody.getMatchType(), matchRequestBody.getTeam1Name(), matchRequestBody.getTeam2Name());

    }

    @PostMapping("/toss")
    public String tossMatch(@RequestParam String matchId, @RequestParam String tossChoice) throws InvalidIdException{
        return matchService.toss(matchId,tossChoice);
    }

    @GetMapping("/playInning1")
    public List<ScoreCard> playInning1(@RequestParam String matchId) throws InvalidIdException,ScoreCardNotFoundException{
        return matchService.playInning1(matchId);
    }

    @GetMapping("/playInning2")
    public List<ScoreCard> playInning2(@RequestParam String matchId) throws InvalidIdException, ScoreCardNotFoundException {
        return matchService.playInning2(matchId);
    }

    @GetMapping("/result")
    public String getResult(@RequestParam String matchId) throws InvalidIdException,ScoreCardNotFoundException {
        return matchService.declareResult(matchId);
    }

}
