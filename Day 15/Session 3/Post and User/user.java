`1)//user
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int userId;
    private String username;
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, List<Post> posts) {
        this.username = username;
        this.email = email;
        this.posts = posts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void add(Post arg0) {
        this.posts.add(arg0);
    }

    
    


}

2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

}

3)//interface
package com.examly.springapp.service;

import com.examly.springapp.model.User;

public interface UserService {

    User addUser(User u);
    User getById(int userId);
    

}

4)//service
package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository urepo;

    @Override
    public User addUser(User u) {
        return urepo.save(u);
    }

    @Override
    public User getById(int userId) {
        return urepo.findById(userId).get();

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

import com.examly.springapp.model.User;
import com.examly.springapp.service.UserServiceImpl;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @PostMapping("/user")
    public ResponseEntity<User> add(@RequestBody User u){
        User u1 = service.addUser(u);
        if(u1 != null){
            return new ResponseEntity<>(u1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> get(@PathVariable int userId){
        User u = service.getById(userId);
        if( u!=null){
            return new ResponseEntity<>(u,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
