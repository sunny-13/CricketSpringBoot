package com.project.cricket.service;

import com.project.cricket.entity.Player;
import com.project.cricket.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;


    public Player getPlayerById(String playerId){
        Optional<Player> player =  playerRepository.findById(playerId);
        return player.orElse(null);
    }

    public Player getPlayerByName(String playerName){
        Player player =  playerRepository.findByPlayerName(playerName);
        return player;
    }

    public Player addPlayer(String playerName, String teamId){
        String playerId = UUID.randomUUID().toString();
        int playerIndex = playerRepository.findByTeamId(teamId).size();
        Player player = new Player(playerId,playerName,teamId,playerIndex);
        return playerRepository.save(player);
    }





}
