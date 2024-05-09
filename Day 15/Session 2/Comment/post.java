1)//post
package com.examly.springapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    
    private String content;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    
    public Post() {
    }
    
    public Post(String title, String content, List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }   
}

2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Post;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

}

3)//interface
package com.examly.springapp.service;

import com.examly.springapp.model.Post;

public interface PostService {

    public Post addPost(Post p);
    public Post getById(int postId);

}

4)//service
package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Post;
import com.examly.springapp.repository.PostRepo;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepo prepo;

    @Override
    public Post addPost(Post p) {
        return prepo.save(p);
    }

    @Override
    public Post getById(int postId) {
        return prepo.findById(postId).get();
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

import com.examly.springapp.model.Post;
import com.examly.springapp.service.PostServiceImpl;

@RestController
public class PostController {

    @Autowired
    private PostServiceImpl service;

    @PostMapping("/post")
    public ResponseEntity<Post> add(@RequestBody Post p){
        Post p1 = service.addPost(p);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> getByIDd(@PathVariable int postId){
        Post p2 = service.getById(postId);
        if(p2 != null){
            return new ResponseEntity<>(p2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
