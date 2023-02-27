package com.project.cricket.repository;

import com.project.cricket.entity.ScoreCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScoreCardRepository extends MongoRepository<ScoreCard,String> {
    List<ScoreCard> findByMatchId(String matchId);
    @Query(value = "{$and:[{'teamId':?0},{'matchId':?1}]}")
    ScoreCard findByTeamIdMatchId(String teamId,String matchId);

    List<ScoreCard> findByTeamName(String teamName);
    List<ScoreCard> findByTeamId(String teamId);
}
