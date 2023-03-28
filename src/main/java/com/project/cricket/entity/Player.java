package com.project.cricket.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Player")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    private String playerId;
    private String playerName;
    private String teamId;
    private int playerIndex;

}
