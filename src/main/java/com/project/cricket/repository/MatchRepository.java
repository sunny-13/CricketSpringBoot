package com.project.cricket.repository;

import com.project.cricket.entity.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match,String> {

}
