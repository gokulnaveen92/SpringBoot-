1)//sport
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Sport {

    @Id
    @GeneratedValue
    private int sportId;
    private String sportName;
    private int playersCount;
    
    public Sport() {
    }

    public Sport(int sportId, String sportName, int playersCount) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.playersCount = playersCount;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }
    
}

2)//service
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Sport;
import com.examly.springapp.repository.SportRepo;

@Service
public class SportService {

    @Autowired
    private SportRepo repo;

    public Sport addSport(Sport s){
        return repo.save(s);
    }

    public Sport getById(int id){
        if(repo.existsById(id)){
            return repo.findById(id).get();
        }
        return null;
    }

    public List<Sport> getAll(){
        return repo.findAll();
    }
    
}


3)//repository
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Sport;

public interface SportRepo extends JpaRepository<Sport,Integer> {

}

4)//controller
  package com.examly.springapp.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Sport;
import com.examly.springapp.service.SportService;

@RestController
public class SportController {

    @Autowired
    private SportService service;

    @PostMapping("/sport")
    public ResponseEntity<Sport> add(@RequestBody Sport s){
        Sport s1 = service.addSport(s);
        if(s1!=null){
            return new ResponseEntity<>(s1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sport/{sportId}")
    public ResponseEntity<Sport> get(@PathVariable int sportId){
        Sport s2 = service.getById(sportId);
        if(s2!=null){
            return new ResponseEntity<>(s2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sport")
    public ResponseEntity<List<Sport>> getAllSports(){
            List<Sport> list = service.getAll();
            if(list.size()>0){
                return new ResponseEntity<>(list,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
    
}


