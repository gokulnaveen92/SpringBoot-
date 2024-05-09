1)//passport
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Passport {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   private String serialNumber;
   private int issueYear;
   private String country;
   
   public Passport() {
    }

public Passport(String serialNumber, int issueYear, String country) {
    this.serialNumber = serialNumber;
    this.issueYear = issueYear;
    this.country = country;
}

public long getId() {
    return id;
}

public void setId(long id) {
    this.id = id;
}

public String getSerialNumber() {
    return serialNumber;
}

public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
}

public int getIssueYear() {
    return issueYear;
}

public void setIssueYear(int issueYear) {
    this.issueYear = issueYear;
}

public String getCountry() {
    return country;
}

public void setCountry(String country) {
    this.country = country;
}
}

2)//repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Passport;

@Repository
public interface PassportRepository extends JpaRepository<Passport,Long>{

}

3)//service
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Passport;
import com.examly.springapp.repository.PassportRepository;
import com.examly.springapp.repository.PersonRepository;

@Service
public class PassportService {

    @Autowired
    private PassportRepository repo;

    @Autowired
    private PersonRepository repo2;

    public Passport addPassport(Passport p){
        return repo.save(p);
    }

    public List<Passport> getAll(){
        return repo.findAll();
    }

    
}

4)//controller
  package com.examly.springapp.controller;

import java.util.List;

import org.apache.catalina.startup.RealmRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Passport;
import com.examly.springapp.service.PassportService;

@RestController
public class PassportController {

    
    @Autowired
    private PassportService service;

    @PostMapping("/passport")
    public ResponseEntity<Passport> add(@RequestBody Passport p){
        Passport p1 = service.addPassport(p);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/passport")
    public ResponseEntity<List<Passport>> getAll(){
        List<Passport> p2 = service.getAll();
        if(p2 != null){
            return new ResponseEntity<>(p2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    

}

