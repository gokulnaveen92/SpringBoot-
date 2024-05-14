package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.entity.Player;

public interface OrganizerService {

    List<Player> getUnsoldPlayers();
    List<Player> getSoldPlayers();
    boolean assignPlayerToTeam(long playerId,long teamId);
    void releasePlayerFromTeam(long playerId);
    List<Player> getPLayerList(long teamId);


}
