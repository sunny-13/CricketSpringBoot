package com.project.cricket.controller;

import com.project.cricket.requestbody.MatchRequestBody;
import com.project.cricket.classes.ScoreCard;

import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.exception.ScoreCardNotFoundException;
import com.project.cricket.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping("/")
    public ResponseEntity<String> createMatch(@RequestBody MatchRequestBody matchRequestBody) throws InvalidIdException{
        return new ResponseEntity<>(matchService.newMatch(matchRequestBody.getMatchType(), matchRequestBody.getTeam1Name(), matchRequestBody.getTeam2Name()), HttpStatus.OK);

    }

    @PostMapping("/toss")
    public ResponseEntity<String> tossMatch(@RequestParam String matchId, @RequestParam String tossChoice) throws InvalidIdException{
        return new ResponseEntity<>( matchService.toss(matchId,tossChoice),HttpStatus.OK);
    }

    @GetMapping("/playInning1")
    public ResponseEntity<List<ScoreCard>> playInning1(@RequestParam String matchId) throws InvalidIdException,ScoreCardNotFoundException{
        return new ResponseEntity<>( matchService.playInning1(matchId),HttpStatus.OK);
    }

    @GetMapping("/playInning2")
    public ResponseEntity<List<ScoreCard>> playInning2(@RequestParam String matchId) throws InvalidIdException, ScoreCardNotFoundException {
        return new ResponseEntity<>(matchService.playInning2(matchId),HttpStatus.OK);
    }

    @GetMapping("/result")
    public ResponseEntity<String> getResult(@RequestParam String matchId) throws InvalidIdException,ScoreCardNotFoundException {
        return new ResponseEntity<>(matchService.declareResult(matchId),HttpStatus.OK);
    }

}
