package com.project.cricket.repository;

import com.project.cricket.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player,String> {

    Player findByPlayerName(String playerName);
    List<Player> findByTeamId(String teamId);

}
