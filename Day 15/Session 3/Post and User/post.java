1)//post
package com.examly.springapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;
    private String title;
    private String content;

    
    @JsonBackReference
    @ManyToOne
    private User user;
    
    public Post() {
    }

    public Post(int postId, String title, String content, User user) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

2)//repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer>{

}

3)//interface
  package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Post;

public interface PostService {
    
    Post addPost(Post p,int userId);
    List<Post> getAll();
    Post getById(int postId);
    Post update(Post p,int postId);
    boolean delete(int postId);
}

4)//spackage com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Post;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.PostRepository;
import com.examly.springapp.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService{

    @Autowired 
    private PostRepository prepo;

    @Autowired
    private UserRepository urepo;


    @Override
    public Post addPost(Post p, int userId) {
        if(urepo.existsById(userId)){
            User u = urepo.findById(userId).get();
            p.setUser(u);
            urepo.save(u);
            return prepo.save(p);
        }else{
            return null;
        }
    }

    @Override
    public List<Post> getAll() {
        return prepo.findAll();
    }

    @Override
    public Post getById(int postId) {
        if(prepo.existsById(postId)){
            return prepo.findById(postId).get();
        }else{
            return null;
        }
    }

    @Override
    public Post update(Post p, int postId) {
        if(prepo.existsById(postId)){
            p.setPostId(postId);
            return prepo.save(p);
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(int postId) {
        if(prepo.existsById(postId)){
            prepo.deleteById(postId);
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.examly.springapp.model.Post;
import com.examly.springapp.service.PostServiceImpl;

@RestController
public class PostController {

    @Autowired
    private PostServiceImpl service;

    @PostMapping("/post/user/{userId}")
    public ResponseEntity<Post> addC(@PathVariable int userId,@RequestBody Post p){
        Post p1  = service.addPost(p, userId);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/post")
    public ResponseEntity<List<Post>> getListC(){
        List<Post> list = service.getAll();
        if(list != null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> getById(@PathVariable int postId){
        Post p = service.getById(postId);
        if(p != null){
            return new ResponseEntity<>(p,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<Post> putPostById(@PathVariable int postId,@RequestBody Post p){
        Post p1 = service.update(p, postId);
        if(p1!=null){
            return new ResponseEntity<>(p1,HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deleteById(@PathVariable int postId){
        boolean b = service.delete(postId);
        if(b){
            return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);    
        }else{
            return new ResponseEntity<>("Post not found with ID: "+postId,HttpStatus.NOT_FOUND);
        }
    }

}
  
