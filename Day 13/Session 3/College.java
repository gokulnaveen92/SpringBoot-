1)//college
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String collegeName;
    private int department;

    public College(long id, String collegeName, int department) {
        this.id = id;
        this.collegeName = collegeName;
        this.department = department;
    }

    public College() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }
    
}

2)//repo
package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.College;

@Repository
public interface CollegeRepo extends JpaRepository<College,Integer>{

    public List<College> findByCollegeNameStartsWith(String name);
    public List<College> findByCollegeNameEndsWith(String name);

}

3)//service
package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.College;
import com.examly.springapp.repository.CollegeRepo;

@Service
public class CollegeService {

    @Autowired
    private CollegeRepo repo;
    
    public College addCollege(College c){
        return repo.save(c);
    }

    public List<College> findByCollegeNameStartsWith(String name){
        return repo.findByCollegeNameStartsWith(name);
    }

    public List<College> findByCollegeNameEndsWith(String name){
        return repo.findByCollegeNameEndsWith(name);
    }

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

import com.examly.springapp.model.College;
import com.examly.springapp.service.CollegeService;

@RestController
public class CollegeController {

    @Autowired
    private CollegeService service;

    @PostMapping("/college")
    public ResponseEntity<College> add(@RequestBody College c){
        return new ResponseEntity<>(service.addCollege(c),HttpStatus.CREATED);
    }

    @GetMapping("/college/startsWith/{name}")
    public ResponseEntity<List<College>> startsWith(@PathVariable String name){
        List<College> list = service.findByCollegeNameStartsWith(name);
        if(!list.isEmpty()){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/college/endsWith/{name}")
    public ResponseEntity<List<College>> endsWith(@PathVariable String name){
        List<College> l2 = service.findByCollegeNameEndsWith(name);
        if(!l2.isEmpty()){
            return new ResponseEntity<>(l2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

}

