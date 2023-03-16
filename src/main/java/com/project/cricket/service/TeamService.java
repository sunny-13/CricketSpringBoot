package com.project.cricket.service;

import com.project.cricket.entity.Player;
import com.project.cricket.entity.Team;
import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerService playerService;


    public Team addTeam(Team team){
        team.setTeamId(UUID.randomUUID().toString());
        return teamRepository.save(team);
    }

    public Team getTeamByName(String teamName) throws InvalidIdException{
        Team team = teamRepository.findByTeamName(teamName);
        if(isNull(team)) throw new InvalidIdException("No team found for given teamName: "+teamName);
        return team;
    }



    public List<String> getMatchListByTeamName(String teamName) throws InvalidIdException{
        Team team = teamRepository.findByTeamName(teamName);
        if(isNull(team)) throw new InvalidIdException("No team found for given teamName: "+teamName);
        return team.getMatchList();
    }


    public void updateMatchList(String teamName, String matchId) throws InvalidIdException {
        Team team = teamRepository.findByTeamName(teamName);
        if (isNull(team)) throw new InvalidIdException("No team found for given teamName: " + teamName);
        team.getMatchList().add(matchId);
        teamRepository.save(team);

    }


    public List<String> getPlayerListByTeamId(String teamName) throws InvalidIdException{
        Team team = teamRepository.findByTeamName(teamName);
        if (isNull(team)) throw new InvalidIdException("No team found for given teamName: " + teamName);
        return team.getPlayerList();
    }

    public List<String> getPlayerIdsByTeamName(String teamName) throws InvalidIdException{
        Team team = teamRepository.findByTeamName(teamName);
        if(isNull(team)) throw new InvalidIdException("No team found for given teamName: "+teamName);
        return team.getPlayerList();
    }

    public List<String> getPlayerNamesByTeamName(String teamName) throws InvalidIdException{
        Team team = teamRepository.findByTeamName(teamName);
        if(isNull(team)) throw new InvalidIdException("No team found for given teamName: "+teamName);
        List<String> playerNames=new ArrayList<>();
        for(int i=0;i<11;i++){
            playerNames.add(playerService.getPlayerById(team.getPlayerList().get(i)).getPlayerName());
        }
        return playerNames;
    }




}


