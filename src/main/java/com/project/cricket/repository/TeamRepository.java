package com.project.cricket.repository;

import com.project.cricket.entity.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team,String> {
    Team findByTeamName(String teamName);

}
