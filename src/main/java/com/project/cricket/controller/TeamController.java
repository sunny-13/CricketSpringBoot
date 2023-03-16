package com.project.cricket.controller;

import com.project.cricket.entity.Team;
import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @PostMapping("/")
    public Team addTeam(@RequestBody Team team){
        return teamService.addTeam(team);
    }

    @GetMapping("/")
    public Team getTeamByName(@RequestParam String teamName) throws InvalidIdException {
        return teamService.getTeamByName(teamName);
    }

    @GetMapping("/matchList")
    public List<String> getMatchListByTeamId(@RequestParam String teamName) throws InvalidIdException{
        return teamService.getMatchListByTeamName(teamName);
    }

    @GetMapping("/playerList")
    public List<String> getPlayerListByTeamName(@RequestParam String teamName) throws InvalidIdException{
        return teamService.getPlayerListByTeamName(teamName);
    }





}


