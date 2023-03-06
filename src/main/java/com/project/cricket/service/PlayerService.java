package com.project.cricket.service;

import com.project.cricket.entity.Player;
import com.project.cricket.entity.ScoreCard;
import com.project.cricket.entity.Team;
import com.project.cricket.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreCardService scoreCardService;
    @Autowired
    private TeamService teamService;


    public Player getPlayerById(String playerId){

        Player player =  playerRepository.findById(playerId).get();
        if(isNull(player)) {
            return null;
        }
        return player;
    }

    public Player getPlayerByName(String playerName){
        return playerRepository.findByPlayerName(playerName);
    }

    public List<String> getMatchListByPlayerId(String playerId){
        return playerRepository.findById(playerId).get().getMatchList();
    }

    public void updateMatchList(List<String> playerList,String matchId){
        try{
            for(int i=0;i<11;i++){
                Player player = getPlayerById(playerList.get(i));
                player.getMatchList().add(matchId);
                playerRepository.save(player);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public Player addPlayer(String playerName, String teamName){
        Team team = teamService.getTeamByName(teamName);
        String playerId = UUID.randomUUID().toString();
        String teamId = team.getTeamId();
        List<String> matchList = new ArrayList<>();
        Player player = new Player(playerId,playerName,teamId,matchList);
        teamService.updatePlayerList(teamId,playerId);
        return playerRepository.save(player);
    }

    public List<Integer> getRunListByPlayerName(String playerName){
        List<Integer> runsList = new ArrayList<>();
        try{

            Player player = getPlayerByName(playerName);
            int index = teamService.getIndexOfPlayer(player.getTeamId(),player.getPlayerId());
            List<ScoreCard> scoreCardList = scoreCardService.getScoreCardsByTeamId(player.getTeamId());
            for(ScoreCard scoreCard: scoreCardList){
                runsList.add(scoreCard.getRunsScored().get(index));
            }
//            runsList = scoreCardList.stream().map(ScoreCard::getRunsScored);

            return runsList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }




}
