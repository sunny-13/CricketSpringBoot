package com.project.cricket.repository;

import com.project.cricket.entity.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeriesRepository extends MongoRepository<Series,String> {

}
