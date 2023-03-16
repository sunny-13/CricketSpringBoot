package com.project.cricket.controller;

import com.project.cricket.entity.Team;
import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @PostMapping("/")
    public ResponseEntity<Team> addTeam(@RequestBody Team team){
        return new ResponseEntity<>(teamService.addTeam(team), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Team> getTeamByName(@RequestParam String teamName) throws InvalidIdException {
        return new ResponseEntity<>(teamService.getTeamByName(teamName),HttpStatus.OK);
    }

    @GetMapping("/matchList")
    public ResponseEntity<List<String>> getMatchListByTeamId(@RequestParam String teamName) throws InvalidIdException{
        return new ResponseEntity<>(teamService.getMatchListByTeamName(teamName),HttpStatus.OK);
    }

    @GetMapping("/playerIds")
    public ResponseEntity<List<String>> getPlayerIdsByTeamName(@RequestParam String teamName) throws InvalidIdException{
        return new ResponseEntity<>( teamService.getPlayerIdsByTeamName(teamName),HttpStatus.OK);
    }

    @GetMapping("/playernames")
    public ResponseEntity<List<String>> getPlayernamesByTeamName(@RequestParam String teamName) throws InvalidIdException{
        return new ResponseEntity<>( teamService.getPlayerNamesByTeamName(teamName),HttpStatus.OK);
    }






}


