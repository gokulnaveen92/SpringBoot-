package com.examly.springapp.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.entity.Team;

@RestController
public class TestController {
    @GetMapping("api/test/welcome")
    public String welcome(){
        return "Welcome to Spring Boot Project";
    }

    @GetMapping("api/test/team")
    public List<Team> getAllTeams(){
        Team t1 = new Team(1,"csk",125476357.00);
        Team t2 = new Team(2,"rcb",152675731.00);
        List<Team> list = new ArrayList<>();
        list.add(t2);
        list.add(t1);
        return list;
        
    }
}
