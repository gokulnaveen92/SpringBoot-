1))//project
package com.examly.springapp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;
    private String projectName;
    
    
    @JsonManagedReference
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<Task> tasks;
    
    public Project() {
    }

    public Project(String projectName, List<Task> tasks) {
        this.projectName = projectName;
        this.tasks = tasks;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer>{


}

3)//service
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

4))//interface
  package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Task;

public interface TaskService {

    public Task addTask(Task t,int projectId);
    public List<Task> getAll();
    public Task getByid(int taskId);
    public boolean delete(int taskId);
    

}

5))//controller
  package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.examly.springapp.model.Task;
import com.examly.springapp.service.TaskServiceImpl;

@RestController
public class TaskController {

    @Autowired
    private TaskServiceImpl service;

    @PostMapping("/task/project/{projectId}")
    public ResponseEntity<Task> addC(@PathVariable int projectId,@RequestBody Task t){
        Task t1 = service.addTask(t, projectId);
        if(t1 != null){
            return new ResponseEntity<>(t1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/task")
    public ResponseEntity<List<Task>> getListC(){
        List<Task> list = service.getAll();
        if(list != null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<Task> getByIdC(@PathVariable int taskId){
        Task t = service.getByid(taskId);
        if(t != null){
            return new ResponseEntity<>(t,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<String> deleteC(@PathVariable int taskId){
        boolean b = service.delete(taskId);
        if(b){
            return new ResponseEntity<>("Task deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Task not found with ID: "+taskId,HttpStatus.NOT_FOUND);
        }
    }

    
}
