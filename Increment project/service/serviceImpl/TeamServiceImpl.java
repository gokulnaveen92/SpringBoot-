package com.examly.springapp.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.entity.Team;
import com.examly.springapp.repository.TeamRepo;
import com.examly.springapp.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepo repo;

    @Override
    public Team addTeam(Team t) {
        return repo.save(t);
       
    }

    @Override
    public Team updateTeam(Team t, int id) {
        if(repo.existsById(id)){
            Team t1 = repo.findById(id).get();
            t1.setName(t.getName());
            t1.setMaximumBudget(t.getMaximumBudget());
            return t1;
        }else{
            return null;
        }
    }

    @Override
    public List<Team> getAllTeam() {
        return repo.findAll();
    }

    @Override
    public Team getByTeamId(int id) {
        if(repo.existsById(id)){
            return repo.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public boolean deleltTeam(int id) {
        repo.deleteById(id);
        return true;
        
    }


}
