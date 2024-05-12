1)//user
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_msg",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private Set<Message> messages = new HashSet<>();

    public User() {
    }

    public User(String name, Set<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

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

    public Set<Message> getMessages() {
        return messages;
    }

    public void add(Message arg0) {
        messages.add(arg0);
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    

}


2)//message
package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "messages",cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    public Message() {
    }

    public Message(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

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

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void add(User arg0) {
        users.add(arg0);
    }

    
}

3)//message repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message,Integer>{

}

4)//user repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

    

}

5)//api service
  
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Message;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.MessageRepo;
import com.examly.springapp.repository.UserRepo;

@Service
public class ApiService {

    @Autowired
    private UserRepo urepo;

    @Autowired
    private MessageRepo mrepo;

    public User addUser(User u){
        return urepo.save(u);
    }

    public boolean addMsgToUser(int userId,Message m){
        if(urepo.existsById(userId)){
            mrepo.save(m);
            User u = urepo.findById(userId).get();
            u.add(m);
            urepo.save(u);
            return true;
        }else{
            return false;
        }
    }

    public List<User> getAllUsers(){
        return urepo.findAll();
    }

    public User getById(int userId){
        return urepo.findById(userId).get();
    }

    public Message updateMsg(int userId,int messageId){
        if(urepo.existsById(userId) && mrepo.existsById(messageId)){
            User u = urepo.findById(userId).get();
            Message m = mrepo.findById(messageId).get();
            u.add(m);
            return mrepo.save(m);     
        }else{
            return null;
        }
    }

    public boolean delete(int userId,int messageId){
        if(urepo.existsById(userId) && mrepo.existsById(messageId)){
            urepo.deleteById(userId);
            mrepo.deleteById(messageId);
            return true;
        }else{
            return false;
        }
    }
}

6)//api controller
  package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.examly.springapp.model.Message;
import com.examly.springapp.model.User;
import com.examly.springapp.service.ApiService;

@RestController
public class ApiController {

    @Autowired
    private ApiService service;

    @PostMapping("/user")
    public ResponseEntity<Boolean> addU(@RequestBody User u){
        User u1 = service.addUser(u);
        if(u1 != null){
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("user/{id}/message")
    public ResponseEntity<Boolean> addM(@PathVariable int id,@RequestBody Message m){
        boolean m1 = service.addMsgToUser(id, m);
        if(m1){
            return new ResponseEntity<>(m1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(m1,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getList(){
        List<User> list = service.getAllUsers();
        if(list != null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> getU(@PathVariable int id){
        User u = service.getById(id);
        if(u != null){
            return new ResponseEntity<>(u,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{userId}/message/{msgId}")
    public ResponseEntity<Message> update(@PathVariable int userId,@PathVariable int msgId){
        Message m = service.updateMsg(userId, msgId);
        if(m!=null){
            return new ResponseEntity<>(m,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{userId}/message/{msgId}")
    public ResponseEntity<Boolean> delete(@PathVariable int userId,@PathVariable int msgId){
        boolean b = service.delete(userId, msgId);
        if(b){
            return new ResponseEntity<>(b,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(b,HttpStatus.OK);
        }
    }
    
}

