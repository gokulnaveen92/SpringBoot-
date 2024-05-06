1)//shirt
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Shirt {

    @Id
    private int shirtId;
    private int shirtSize;
    private String shirtColor;

    

    public Shirt() {
    }

    public Shirt(int shirtId, int shirtSize, String shirtColor) {
        this.shirtId = shirtId;
        this.shirtSize = shirtSize;
        this.shirtColor = shirtColor;
    }

    public int getShirtId() {
        return shirtId;
    }

    public void setShirtId(int shirtId) {
        this.shirtId = shirtId;
    }

    public int getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(int shirtSize) {
        this.shirtSize = shirtSize;
    }

    public String getShirtColor() {
        return shirtColor;
    }

    public void setShirtColor(String shirtColor) {
        this.shirtColor = shirtColor;
    }
}

2)//service
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Shirt;
import com.examly.springapp.repository.ShirtRepo;

@Service
public class ShirtService {

    @Autowired
    private ShirtRepo repo;

    public Shirt addShirt(Shirt s){
        return repo.save(s);
    }

    public List<Shirt> getAll(){
        return repo.findAll();
    }

    public Shirt getById(int id){
        if(repo.existsById(id)){
            return repo.findById(id).get();
        }else{
            return null;
        }
    }

    public Shirt updateById(Shirt s,int id){
        if(repo.existsById(id)){
            Shirt s1 = repo.findById(id).get();
            s1.setShirtId(s.getShirtId());
            s1.setShirtSize(s.getShirtSize());
            s1.setShirtColor(s.getShirtColor());
            return s1;
        }else{
            return null;
        }
    }

    public boolean delById(int id){
        repo.deleteById(id);
        if(repo.existsById(id)){
            return false;
        }else{
            return true;
        }
    }

}

3)//repository
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Shirt;

@Repository
public interface ShirtRepo extends JpaRepository<Shirt,Integer> {

}

4)//controller
package com.examly.springapp.controller;

import java.util.List;

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

import com.examly.springapp.model.Shirt;
import com.examly.springapp.service.ShirtService;

@RestController
public class ShirtController {

    @Autowired
    private ShirtService service;

    @PostMapping("/shirt")
    public ResponseEntity<Shirt> add(@RequestBody Shirt s){
        Shirt s1 = service.addShirt(s);
        if(s1!=null){
            return new ResponseEntity<>(s1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/shirt")
    public ResponseEntity<List<Shirt>> getAllShirts(){
        List<Shirt> list = service.getAll();
        if(!list.isEmpty()){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("shirt/{shirtId}")
    public ResponseEntity<Shirt> getId(@PathVariable int shirtId){
        Shirt s3 = service.getById(shirtId);
        if(s3!=null){
            return new ResponseEntity<>(s3,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/shirt/{shirtId}")
    public ResponseEntity<Shirt> update(@RequestBody Shirt s,@PathVariable int shirtId){
        Shirt s4 = service.updateById(s, shirtId);
        if(s4!=null){
            return new ResponseEntity<>(s4,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/shirt/{shirtId}")
    public ResponseEntity<Boolean>  del(@PathVariable int shirtId){
        Boolean s5 = service.delById(shirtId);
        if(s5.booleanValue()){
            return new ResponseEntity<>(s5,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(s5,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
  
