1)//user
package com.example.springapp.model;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
 
import java.util.*;
 
@Entity
@Table(name = "usr")
public class User {
 
    @Id
    @GeneratedValue
    private int id;
    private String name;
   
    @ManyToMany
    @JoinTable(
        name = "user_group",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name ="group_id")
    )
    private Set<Group> groups = new HashSet<>();
 
    public User(){}
 
    public User(String name, Set<Group> groups) {
        this.name = name;
        this.groups = groups;
    }
 
    public int getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Set<Group> getGroups() {
        return groups;
    }
 
    public void addGroup(Group group){
        groups.add(group);
    }
 
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}

2)//group
package com.example.springapp.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="grp")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    
    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new HashSet<>();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users.add(users);
    }

    public Group(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Group() {
    }
}


3)//userRepo
package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

}

4)//groupRepo
package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer>{



}

5)//apiservice
package com.example.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Group;
import com.example.springapp.model.User;
import com.example.springapp.repository.GroupRepository;
import com.example.springapp.repository.UserRepository;

import jakarta.persistence.Id;

@Service
public class ApiService {

    @Autowired
    private GroupRepository grepo;

    @Autowired 
    private UserRepository urepo;

    public Group addGroup(Group g){
        return grepo.save(g);
    }

    public User addUser(User u){
        return urepo.save(u);
    }

    public Group addUserToGroup(int groupId,int userId){
        if(grepo.existsById(groupId) && urepo.existsById(userId)){
            Group g = grepo.findById(groupId).get();
            User u = urepo.findById(userId).get();
            g.setUsers(u);
            return grepo.save(g);
        }else{
            return null;
        }
    }

    public List<User> getAll(){
        return urepo.findAll();
    }

    public Group getById(int groupId){
        return grepo.findById(groupId).get();
    }
}


6)//controller
package com.example.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Group;
import com.example.springapp.model.User;
import com.example.springapp.service.ApiService;

@RestController
public class ApiController {

    @Autowired
    private ApiService service;

    @PostMapping("/api/groups")
    public ResponseEntity<Group> add(@RequestBody Group m){
        Group m1 = service.addGroup(m);
        if(m1 != null){
            return new ResponseEntity<>(m1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/users")
    public ResponseEntity<User> add(@RequestBody User m){
        User m1 = service.addUser(m);
        if(m1 != null){
            return new ResponseEntity<>(m1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/groups/{groupId}/addUser/{userId}")
    public ResponseEntity<Group> addUserToGroup(@PathVariable int groupId,@PathVariable int userId){
        Group m1 = service.addUserToGroup(groupId, userId);
        if(m1 != null){
            return new ResponseEntity<>(m1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAll(){
        List<User> m1 = service.getAll();
        if(m1 != null){
            return new ResponseEntity<>(m1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("api/groups/{groupId}")
    public ResponseEntity<Group> getById(@PathVariable int groupId){
        Group m1 = service.getById(groupId);
        if(m1 != null){
            return new ResponseEntity<>(m1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}
