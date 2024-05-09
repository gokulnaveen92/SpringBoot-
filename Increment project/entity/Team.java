package com.examly.springapp.entity;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Team {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private double maximumBudget;

    @JsonIgnore
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> players;
    

    public Team() {
    }

    public Team(long id, String name, double maximumBudget) {
        this.id = id;
        this.name = name;
        this.maximumBudget = maximumBudget;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaximumBudget() {
        return maximumBudget;
    }

    public void setMaximumBudget(double maximumBudget) {
        this.maximumBudget = maximumBudget;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

  

    


}
