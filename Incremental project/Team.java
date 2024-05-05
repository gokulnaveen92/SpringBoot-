package com.examly.springapp.entity;

import java.util.ArrayList;
import java.util.List;


public class Team {
    private long id;
    private String name;
    private double maximumBudget;

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


    


}
