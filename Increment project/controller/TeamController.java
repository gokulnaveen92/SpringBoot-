package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.entity.Team;
import com.examly.springapp.service.serviceImpl.TeamServiceImpl;

@RestController
public class TeamController {

    @Autowired
    private TeamServiceImpl service;

    @PostMapping("api/team")
    public Team add(@RequestBody Team t){
        Team t2 = service.addTeam(t);
        if(t2!=null){
            return t2;
        }else{
            return null;
        }
    }

    @PutMapping("api/team/{teamId}")
    public Team update(@RequestBody Team t,@PathVariable int teamId){
        Team t3 = service.updateTeam(t, teamId);
        if(t3!=null){
            return t3;
        }else{
            return null;
        }
    }

    @GetMapping("api/team")
    public List<Team> getAllTeams(){
        List<Team> list = service.getAllTeam();
        if(!list.isEmpty()){
            return list;
        }else{
            return null;
        }
    }

    @GetMapping("api/team/{teamId}")
    public Team getById(@PathVariable int teamId){
        Team t3 = service.getByTeamId(teamId);
        if(t3!=null){
            return t3;
        }else{
            return null;
        }
    }

    @DeleteMapping("api/team/{teamId}")
    public boolean delete(@PathVariable int teamId ){
        boolean b1 =service.deleltTeam(teamId);
        return b1;
        

    }

}
