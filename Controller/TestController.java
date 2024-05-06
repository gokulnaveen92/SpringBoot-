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
        Team t1 = new Team(1,"CSK",1000000000.00);
        Team t2 = new Team(2,"RCB",1000000000.00);
        Team t3 = new Team(3,"MI",1000000000.00);
        Team t4 = new Team(4,"LSG",1000000000.00);
        Team t5 = new Team(5,"GT",1000000000.00);
        Team t6 = new Team(6,"DC",1000000000.00);
        Team t7 = new Team(7,"KKR",1000000000.00);
        Team t8 = new Team(8,"PBKS",1000000000.00);
        Team t9 = new Team(9,"SRH",1000000000.00);
        Team t10 = new Team(10,"RR",1000000000.00);
        List<Team> list = new ArrayList<>();
        list.add(t2);
        list.add(t1);
        list.add(t3);
        list.add(t4);
        list.add(t5);
        list.add(t6);
        list.add(t7);
        list.add(t8);
        list.add(t9);
        list.add(t10);
        return list;
        
    }
}
