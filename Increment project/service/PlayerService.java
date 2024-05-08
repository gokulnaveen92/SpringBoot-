package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.entity.Player;

public interface PlayerService {

    public Player addPlayer(Player p);
    public Player updatePlayer(long playerId,Player p);
    public List<Player> getAll();
    public Player getByPlayerId(long playerId);
    public boolean deletByPlayerId(long playerId);

}
