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

    @PostMapping("/new")
    public ScoreCard getNewScorecard(@RequestBody ScoreCard scoreCard){
        return scoreCardService.addScoreCard(scoreCard);
    }

    @GetMapping("/getById")
    public ScoreCard getScoreCardById(@RequestParam  String scoreCardId){
        return scoreCardService.getScoreCardById(scoreCardId);
    }

    @GetMapping("/getByTeamAndMatch")
    public ScoreCard getScoreCard(@RequestParam String teamId,@RequestParam String matchId){
        return scoreCardService.getScoreCardByTeamIdMatchId(teamId,matchId);
    }

    @GetMapping("/getListByTeamName")
    public List<ScoreCard> getScoreCardListByTeamName(@RequestParam String  teamName){
        return scoreCardService.getScoreCardsByTeamName(teamName);
    }

    @GetMapping("/getByMatchId")
    public List<ScoreCard> getScoreCardsByMatchId(@RequestParam String  matchId){
        return scoreCardService.getScoreCardsByMatchId(matchId);
    }

}
