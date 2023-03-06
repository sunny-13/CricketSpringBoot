package com.project.cricket.controller;

import com.project.cricket.entity.Player;
import com.project.cricket.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/create")
    public Player addPlayer(@RequestParam String playerName,@RequestParam String teamName){
        return playerService.addPlayer(playerName,teamName);
    }
    @GetMapping("/run_list")
    public List<Integer> getTotalRunsByPlayerId(@RequestParam String playerName){
        return playerService.getRunListByPlayerName(playerName);
    }

}
