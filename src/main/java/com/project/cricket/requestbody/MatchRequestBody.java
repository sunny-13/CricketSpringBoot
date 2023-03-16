package com.project.cricket.requestbody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequestBody {
    private String team1Name;
    private String team2Name;
    private String matchType;
}
