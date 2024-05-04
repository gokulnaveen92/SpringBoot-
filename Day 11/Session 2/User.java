1)//user
package com.example.springapp.model;

public class User {
    private long id;
    private String name;
    private String email;
    private int age;
    private String address;

    
    public User(long id, String name, String email, int age, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    
}

2)//controller
package com.example.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.User;
import com.example.springapp.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/api/user")
    public ResponseEntity<User> addUser(@RequestBody User u){
        User u1 = service.addUser(u);
        if(u1!=null){
            return new ResponseEntity<>(u1,HttpStatus.OK);
        }else{
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("api/user")
    public ResponseEntity<List<User>> get(){
        List<User> h = service.getAll();
        if(h.size()>0){
            return new ResponseEntity<>(h,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

3)//service
package com.example.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springapp.model.User;

@Service
public class UserService {
    List<User> list = new ArrayList<>();

    public User addUser(User u){
        list.add(u);
        return u;
    }

    public List<User> getAll(){
        return list;
    }

}
