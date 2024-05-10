1)//job
package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;

    @ManyToMany
    @JoinTable(
        name = "job_skill",
        joinColumns = @JoinColumn(name = "job_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    public Job() {
    }

    public Job(String title, String description, Set<Skill> skills) {
        this.title = title;
        this.description = description;
        this.skills = skills;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public void addSet(Skill s){
        this.skills.add(s);
    }

}

2)//skill
package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    
    @ManyToMany(mappedBy = "skills")
    private Set<Job> jobs = new HashSet<>();

    public Skill() {
    }

    public Skill(String name, Set<Job> jobs) {
        this.name = name;
        this.jobs = jobs;
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

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    





}

3)//jobrepo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

}

4)//skillrepo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Skill;

@Repository
public interface SkillsRepository extends JpaRepository<Skill,Long>{

}

5)//jobskill service
package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Job;
import com.examly.springapp.model.Skill;
import com.examly.springapp.repository.JobRepository;
import com.examly.springapp.repository.SkillsRepository;

@Service
public class JobSkillService {

    @Autowired
    private JobRepository jrepo;

    @Autowired 
    private SkillsRepository srepo;
    
    public Job add(Job j){
        return jrepo.save(j);
    }

    public Skill addSkill(Skill s){
        return srepo.save(s);
    }

    public Job addSkillToJob(long jobId,long skillId){
        Job j1;
        Skill s1;
        if(srepo.existsById(skillId) && jrepo.existsById(jobId)){
            j1 = jrepo.findById(jobId).get();
            s1 = srepo.findById(skillId).get();
            j1.addSet(s1);
            return jrepo.save(j1);    
        }else{
            return null;
        }
    }

    public List<Job> getAllJobs(){
        return jrepo.findAll();
    }

    public List<Skill> getAllSkills(){
        return srepo.findAll();
    }

    public boolean delete(long id){
        if(jrepo.existsById(id)){
            jrepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}

6)//apicontroller
package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Job;
import com.examly.springapp.model.Skill;
import com.examly.springapp.service.JobSkillService;

@RestController
public class ApiController {

    @Autowired
    private JobSkillService service;

    @PostMapping("/api/jobs")
    public ResponseEntity<Job> addJob(@RequestBody Job j){
        Job j1 = service.add(j);
        if(j1 != null){
            return new ResponseEntity<>(j1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/skills")
    public ResponseEntity<Skill> addSkill(@RequestBody Skill j){
        Skill j2 = service.addSkill(j);
        if(j2 != null){
            return new ResponseEntity<>(j2,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/jobs/{jobId}/add-skill/{skillId}")
    public ResponseEntity<Job> addSkillToJob(@PathVariable int jobId,@PathVariable int skillId){
        Job j3 = service.addSkillToJob(jobId, skillId);
        if(j3 != null){
            return new ResponseEntity<>(j3,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/api/jobs")
    public ResponseEntity<List<Job>> getAllJobs(){
        List<Job> j4 = service.getAllJobs();
        if(j4 != null){
            return new ResponseEntity<>(j4,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/api/skills")
    public ResponseEntity<List<Skill>> getAllSkills(){
        List<Skill> j4 = service.getAllSkills();
        if(j4 != null){
            return new ResponseEntity<>(j4,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/api/jobs/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        boolean j4 = service.delete(id);
        if(j4){
            return new ResponseEntity<>(j4,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
