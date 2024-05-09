1)//person
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Person {
    
    @OneToOne
    @JoinColumn(name = "id")
    private Address address;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String nationality;
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person(String name, String email, String phoneNumber, String nationality) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nationality = nationality;
    }

    public Person() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

}

2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Person;

public interface PersonRepository extends JpaRepository<Person,Long> {

}

3)//service
package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Address;
import com.examly.springapp.model.Person;
import com.examly.springapp.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repo;

    public Person add(Person p){
        return repo.save(p);
    }

    public List<Person> getAll(){
        return repo.findAll();
    }

    public Person getById(long personId){
        if(repo.existsById(personId)){
            return repo.findById(personId).get();
        }else{
            return null;
        }
    }

}

4)//controller
package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Person;
import com.examly.springapp.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/person")
    public ResponseEntity<Person> add(@RequestBody Person p){
        Person p1 = service.add(p);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/person")
    public ResponseEntity<List<Person>> getall(){
        List<Person> p2 = service.getAll();
        if(p2 != null){
            return new ResponseEntity<>(p2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<Person> getbyId(@PathVariable long personId){
        Person p3 = service.getById(personId);
        if(p3 != null){
            return new ResponseEntity<>(p3,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
