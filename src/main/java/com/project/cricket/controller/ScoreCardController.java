package com.project.cricket.controller;

import com.project.cricket.entity.ScoreCard;
import com.project.cricket.service.ScoreCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scorecard")
public class ScoreCardController {
    @Autowired
    private ScoreCardService scoreCardService;

    @PostMapping("/create")
    public ScoreCard getNewScorecard(@RequestBody ScoreCard scoreCard){
        return scoreCardService.addScoreCard(scoreCard);
    }

    @GetMapping("")
    public ScoreCard getScoreCardById(@RequestParam("scoreCardId")  String scoreCardId){
        return scoreCardService.getScoreCardById(scoreCardId);
    }

    @GetMapping("/team_match")
    public ScoreCard getScoreCard(@RequestParam String teamId,@RequestParam String matchId){
        return scoreCardService.getScoreCardByTeamIdMatchId(teamId,matchId);
    }

    @GetMapping("/team")
    public List<ScoreCard> getScoreCardListByTeamName(@RequestParam("teamName") String  teamName){
        return scoreCardService.getScoreCardsByTeamName(teamName);
    }

    @GetMapping("/match")
    public List<ScoreCard> getScoreCardsByMatchId(@RequestParam String  matchId){
        return scoreCardService.getScoreCardsByMatchId(matchId);
    }

}
