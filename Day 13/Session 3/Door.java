1)//door
package com.example.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Door {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int doorId;
    private String doorType;
    private String material;
    private String color;
    private int price;

    public Door() {
    }

    public Door(int doorId, String doorType, String material, String color, int price) {
        this.doorId = doorId;
        this.doorType = doorType;
        this.material = material;
        this.color = color;
        this.price = price;
    }

    public int getDoorId() {
        return doorId;
    }

    public void setDoorId(int doorId) {
        this.doorId = doorId;
    }

    public String getDoorType() {
        return doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

2)//repo
package com.example.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Door;

@Repository
public interface DoorRepo extends JpaRepository<Door,Integer> {

    public List<Door> findByColor(String color);

    //@Query(value="UPDATE FROM Door SET color = ?1 WHERE doorId = ?2")
    //public List<Door> updateColor(String color,int doorId);
    

    public List<Door> findByDoorType(String doorType);

}

3)//service
  package com.example.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Door;
import com.example.springapp.repository.DoorRepo;

@Service
public class DoorService {

    @Autowired
    private DoorRepo repo;

    public Door addDoor(Door d){
        return repo.save(d);
    }

    public List<Door> findByColor(String color){
        return repo.findByColor(color);
    }

    public Door updateDoor(String color,int id){
        if(repo.existsById(id)){
            Door d3 = repo.findById(id).get();
            d3.setColor(color);
            return d3;
        }else{
            return null;
        }
    }
    public boolean delete(int id){
        if(repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }else{
            return false;
        }

    }

    public List<Door> byDoorTyp(String doorType){
        return repo.findByDoorType(doorType);
    }

    public List<Door> getAllDoors(){
        return repo.findAll();    
    }

    public Door getByDoorId(int id){
        return repo.findById(id).get();
    }

}

4)//controller
  package com.example.springapp.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Door;
import com.example.springapp.service.DoorService;

@RestController
public class DoorController {

    @Autowired
    private DoorService service;

    @PostMapping("/api/door")
    public ResponseEntity<Door> add(@RequestBody Door d){
        Door d1 = service.addDoor(d);
        if(d1!=null){
            return new ResponseEntity<>(d1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("api/door/byColor/{color}")
    public ResponseEntity<List<Door>> byColor(@PathVariable String color){
        List<Door> d2 = service.findByColor(color);
        if(d2!=null){
            return new ResponseEntity<>(d2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/api/door/{doorId}")
    public ResponseEntity<Door> updateColor(@RequestParam String color,@PathVariable int doorId){
        Door d3 = service.updateDoor(color, doorId);
        if(d3!=null){
            return new ResponseEntity<>(d3,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    @DeleteMapping("/api/door/{doorId}")
    public ResponseEntity<String> delete(@PathVariable int doorId){
        boolean d4 = service.delete(doorId);
        if(d4){
            return new ResponseEntity<>("Door deleted Successfully.",HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }
    
    @GetMapping("/api/door/bydoorType")
    public ResponseEntity<List<Door>> byDoorType(@RequestParam String doorType){
        List<Door> list = service.byDoorTyp(doorType);
        if(list!=null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }
    
    @GetMapping("/api/door")
    public ResponseEntity<List<Door>> getALl(){
        List<Door> list = service.getAllDoors();
        if(list!=null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }
    
    @GetMapping("/api/door/{doorId}")
    public ResponseEntity<Door> getById(@PathVariable int doorId){
        Door d5 = service.getByDoorId(doorId);
        if(d5!=null){
            return new ResponseEntity<>(d5,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    
    



}


