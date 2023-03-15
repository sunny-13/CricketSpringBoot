package com.project.cricket.repository;

import com.project.cricket.entity.BattingScoreCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BattingScoreCardRepository extends MongoRepository<BattingScoreCard,String> {
    @Query(value = "{$and:[{'matchId':?0},{'teamName':?1}]}")
    BattingScoreCard findByMatchIdTeamName(String matchId,String teamName);

    List<BattingScoreCard> findByMatchId(String matchId);
}
