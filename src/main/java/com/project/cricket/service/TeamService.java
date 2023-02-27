package com.project.cricket.service;

import com.project.cricket.entity.Team;
import com.project.cricket.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;


    public Team addTeam(Team team){
        team.setTeamId(UUID.randomUUID().toString());
        return teamRepository.save(team);
    }

    public Team getTeamById(String teamId){
        Optional<Team> team =  teamRepository.findById(teamId);
        if(team.isPresent()) return team.get();
        return null;

    }

    public Team getTeamByName(String teamName){
        return teamRepository.findByTeamName(teamName);

    }

    public String getTeamIdByName(String teamName){
        return teamRepository.findByTeamName(teamName).getTeamId();

    }



    public List<String> getMatchListByTeamName(String teamName){
        return teamRepository.findByTeamName(teamName).getMatchList();
    }


    public void updateMatchList(String teamId, String matchId){
        try{
            Team team = teamRepository.findById(teamId).get();
            team.getMatchList().add(matchId);
            teamRepository.save(team);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void updatePlayerList(String teamId, String playerId){
        try{
            Team team = teamRepository.findById(teamId).get();
            team.getPlayerList().add(playerId);
            teamRepository.save(team);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public List<String> getPlayerListByTeamId(String teamId){
        return teamRepository.findById(teamId).get().getPlayerList();
    }

    public List<String> getPlayerListByTeamName(String teamName){
        return teamRepository.findByTeamName(teamName).getPlayerList();
    }


    public int getIndexOfPlayer(String teamId, String playerId){
        return getTeamById(teamId).getPlayerList().indexOf(playerId);
    }


    //    public List<String> getMatchListByTeamId(String teamId){
//        return teamRepository.findById(teamId).get().getMatchList();
//    }


}


