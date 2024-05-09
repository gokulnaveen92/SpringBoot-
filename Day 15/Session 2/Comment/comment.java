1)//comment
package com.examly.springapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String text;
    
    
    @JsonBackReference
    @ManyToOne
    private Post post;
    
    public Comment() {
    }

    public Comment(int id, String text, Post post) {
        this.id = id;
        this.text = text;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}

2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {

}

3)//interface
package com.examly.springapp.service;

import com.examly.springapp.model.Comment;

public interface CommentService {
    
    public Comment addComment(Comment c , int postId);
    public Comment updateComment(Comment c, int commentId);
    public boolean deleteComment(int commentId);
    

}

4)//service
package com.examly.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Comment;
import com.examly.springapp.model.Post;
import com.examly.springapp.repository.CommentRepo;
import com.examly.springapp.repository.PostRepo;

@Service
public class CommentServiceImpl implements CommentService{
    
    @Autowired
    private CommentRepo crepo;

    @Autowired
    private PostRepo prepo;

    List<Comment> c1 = new ArrayList<>();

    @Override
    public Comment addComment(Comment c, int postId) {
        if(prepo.existsById(postId)){
            Post p1 = prepo.findById(postId).get();
            c1.add(crepo.save(c));
            p1.setComments(c1);
            return c;   
        }else{
            return null;
        }
    }

    @Override
    public Comment updateComment(Comment c, int commentId) {
        if(crepo.existsById(commentId)){
            c.setId(commentId);
            return crepo.save(c);
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteComment(int commentId) {
        if(crepo.existsById(commentId)){
            crepo.deleteById(commentId);
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Comment;
import com.examly.springapp.service.CommentServiceImpl;

@RestController
public class CommentController {

    @Autowired
    private CommentServiceImpl service;

    @PostMapping("/comment/post/{postId}")
    public ResponseEntity<Comment> add(@RequestBody Comment p,@PathVariable int postId){
        Comment p1 = service.addComment(p, postId);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Comment> update(@RequestBody Comment p,@PathVariable int commentId){
        Comment p2 = service.updateComment(p, commentId);
        if(p2 != null){
            return new ResponseEntity<>(p2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> update(@PathVariable int commentId){
        boolean  p3 = service.deleteComment(commentId);
        if(p3){
            return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Comment not found with ID "+commentId,HttpStatus.OK);
        }
    }
    
    
}
