package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.entity.Player;
import com.examly.springapp.service.PlayerService;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService service;

    @PostMapping("api/player")
    public Player add(@RequestBody Player p){
        return service.addPlayer(p);
    }

    @PutMapping("api/player/{playerId}")
    public Player update(@PathVariable long playerId,@RequestParam Player P){
        return service.updatePlayer(playerId, P);
    }

    @GetMapping("api/player")
    public List<Player> getAll(){
        return service.getAll();
    }

    @GetMapping("api/palyer/{playerId}")
    public Player getBPlayerId(@PathVariable int playerId){
        return service.getByPlayerId(playerId);
    }
}
