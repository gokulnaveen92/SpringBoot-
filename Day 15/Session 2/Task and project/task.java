1)//task
package com.examly.springapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int taskId;
    private String title;
    private String description;
    
    @JsonBackReference
    @ManyToOne
    private Project project;
    
    public Task() {
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Task;

import jakarta.persistence.criteria.CriteriaBuilder.In;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

}

3)//interface
package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Task;

public interface TaskService {

    public Task addTask(Task t,int projectId);
    public List<Task> getAll();
    public Task getByid(int taskId);
    public boolean delete(int taskId);
    

}

4)//service
package com.examly.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Project;
import com.examly.springapp.model.Task;
import com.examly.springapp.repository.ProjectRepository;
import com.examly.springapp.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository trepo;

    @Autowired
    private ProjectRepository prepo;

    List<Task> tasks = new ArrayList<>();

    @Override
    public Task addTask(Task t, int projectId) {
        if(prepo.existsById(projectId)){
            Project p1 = prepo.findById(projectId).get();
            t.setProject(p1);
            return trepo.save(t);
        }else{
            return null;
        }
    }

    @Override
    public List<Task> getAll() {
        return trepo.findAll();
    }

    @Override
    public Task getByid(int taskId) {
      if(trepo.existsById(taskId)){ 
        return trepo.findById(taskId).get();
      }else{
        return null;
      }
    }

    @Override
    public boolean delete(int taskId) {
        if(trepo.existsById(taskId)){
            trepo.deleteById(taskId);
            return true;
        }else{
            return false;
        }
    }

}

5)//controller
package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Project;
import com.examly.springapp.service.ProjectServiceImpl;


@RestController
public class ProjectController {

    @Autowired
    private ProjectServiceImpl service;


    @PostMapping("/project")
    public ResponseEntity<Project> addC(@RequestBody Project p){
        Project p1 = service.addPost(p);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Project> getByIdC(@PathVariable int projectId){
        Project p1 = service.getById(projectId);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }
    
}
