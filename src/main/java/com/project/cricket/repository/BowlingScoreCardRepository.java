package com.project.cricket.repository;

import com.project.cricket.entity.BowlingScoreCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BowlingScoreCardRepository extends MongoRepository<BowlingScoreCard,String> {

    @Query(value = "{$and:[{'matchId':?0},{'teamName':?1}]}")
    BowlingScoreCard findByMatchIdTeamName(String matchId, String teamName);

    List<BowlingScoreCard> findByMatchId(String matchId);
}
