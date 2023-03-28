package com.project.cricket.controller;

import com.project.cricket.classes.ScoreCard;
import com.project.cricket.exception.ScoreCardNotFoundException;
import com.project.cricket.service.ScoreCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scorecard")
public class ScoreCardController {
    @Autowired
    private ScoreCardService scoreCardService;

    @GetMapping("/")
    public ResponseEntity<List<ScoreCard>> getScoreCardByMatchId(@RequestParam String matchId) throws ScoreCardNotFoundException {
        return new ResponseEntity<>(scoreCardService.findByMatchId(matchId), HttpStatus.OK);
    }



}
