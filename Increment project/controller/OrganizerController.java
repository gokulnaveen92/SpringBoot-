package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.entity.Player;
import com.examly.springapp.service.serviceImpl.OrganizerServiceImpl;

@RestController
public class OrganizerController {

    @Autowired
    private OrganizerServiceImpl service;

    @PostMapping("api/organizer/assign-player")
    public boolean addPlayerToTeam(@RequestParam long playerId,@RequestParam long teamId){
        return service.assignPlayerToTeam(playerId, teamId);
    }

    @GetMapping("api/organizer/unsold-players")
    public List<Player> getUnsoldPlayers(){
        return service.getUnsoldPlayers();
    }

    @GetMapping("api/organizer/sold-players")
    public List<Player> getSoldPlayers(){
        return service.getSoldPlayers();
    }

    @GetMapping("api/organizer/player-list/{teamId}")
    public List<Player> getPlayersOfTeam(@PathVariable long teamId){
        return service.getPLayerList(teamId);
    }

    @PutMapping("api/organizer/release-player/{playerId}")
    public boolean releasePlayerFromTeam(@PathVariable long playerId){
        service.releasePlayerFromTeam(playerId);
        return true;
    }

}
