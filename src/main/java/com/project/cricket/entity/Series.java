package com.project.cricket.entity;

import com.project.cricket.classes.MatchType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "series")
@Data
@NoArgsConstructor
public class Series {
    @Id
    private String seriesId;
    private List<String> teams;
    private MatchType seriesType;
    private int noOfMatches;
    private List<String> matchIds;


}
