1)//Job
package com.example.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Job {

    @Id
    @GeneratedValue
    private int jobId;
    private String jobTitle;
    private int minSalary;
    private String jobDescription;
    private int maxSalary;

    public Job() {
    }

    public Job(int jobId, String jobTitle, int minSalary, String jobDescription, int maxSalary) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.minSalary = minSalary;
        this.jobDescription = jobDescription;
        this.maxSalary = maxSalary;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }  

}


2)//repository
  package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Job;

@Repository
public interface JobRepo extends JpaRepository<Job,Integer> {

}


3)//service
  package com.example.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Job;
import com.example.springapp.repository.JobRepo;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;

    public Job addJob(Job l){
        return repo.save(l);
    }

    public List<Job> getAll(){
        return repo.findAll();
    }

    public Job getById(int id){
        if(repo.existsById(id)){
            return repo.findById(id).get();
        }else{
            return null;
        }
    }


}

4)//controller
  package com.example.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Job;
import com.example.springapp.service.JobService;

@RestController
public class JobController {

    @Autowired
    private JobService service;

    @PostMapping("/api/job")
    public ResponseEntity<Job> add(@RequestBody Job l){
        Job l2 = service.addJob(l);
        if(l2!=null){
            return new ResponseEntity<>(l2,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/job")
    public ResponseEntity<List<Job>> getAll(){
        List<Job> list = service.getAll();
        if(!list.isEmpty()){
           return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/job/{jobId}")
    public ResponseEntity<Job> getId(@PathVariable int jobId){
        Job l2 = service.getById(jobId);
        if(l2!=null){
            return new ResponseEntity<>(l2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

