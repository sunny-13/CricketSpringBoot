package com.project.cricket.controller;

import com.project.cricket.entity.Player;
import com.project.cricket.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/")
    public ResponseEntity<Player> addPlayer(@RequestParam String playerName, @RequestParam String teamName){
        return new ResponseEntity<>( playerService.addPlayer(playerName,teamName), HttpStatus.OK);
    }

}
