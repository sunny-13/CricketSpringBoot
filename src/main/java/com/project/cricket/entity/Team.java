package com.project.cricket.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Team")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    private String teamId;
    private String teamName;
    private List<String> matchList;
    private List<String> playerList;
    private int wins;
}
