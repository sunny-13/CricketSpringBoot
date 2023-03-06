package com.project.cricket.controller;

import com.project.cricket.entity.Team;
import com.project.cricket.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @PostMapping("/create")
    public Team addTeam(@RequestBody Team team){
        return teamService.addTeam(team);
    }

    @GetMapping("/")
    public Team getTeamByName(@RequestParam String teamName){
        return teamService.getTeamByName(teamName);
    }

    @GetMapping("/match_list")
    public List<String> getMatchListByTeamId(@RequestParam String teamName){
        return teamService.getMatchListByTeamName(teamName);
    }

    @GetMapping("/player_list")
    public List<String> getPlayerListByTeamName(@RequestParam String teamName){
        return teamService.getPlayerListByTeamName(teamName);
    }





}


