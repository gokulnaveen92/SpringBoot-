package com.examly.springapp.service;

import com.examly.springapp.entity.Team;

public interface TeamService {

    public Team addTeam(Team t);
    public Team updateTeam(Team t , int id);

}
