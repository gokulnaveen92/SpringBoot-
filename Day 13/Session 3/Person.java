1))//person
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstname;
    private String lastname;
    private int age;

    public Person(long id, String firstname, String lastname, int age) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public Person() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }  

    
}

2)
package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person,Long> {

    public List<Person> findByLastnameNot(String lastname);
    public List<Person> findByAgeNotIn(List<Integer> ages);

}


3)
package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Person;
import com.examly.springapp.repository.PersonRepo;


@Service
public class PersonService {

    @Autowired
    private PersonRepo repo;
    

    public Person addPerson(Person p){
        if(!repo.existsById(p.getId()))
        return repo.save(p);
        return null;
      
    }


    public List<Person> findByLastnameNot(String lastname){
        return repo.findByLastnameNot(lastname);
    }


    public List<Person> findByAgeNotIn(List<Integer> ages){
        return repo.findByAgeNotIn(ages);
    }
}

4)
package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Person;
import com.examly.springapp.service.PersonService;


@RestController
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping("/person")
    public ResponseEntity<Person> add(@RequestBody Person p){
        Person p1 = service.addPerson(p);
        if(p1!=null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/person/findByLastnameNot")
    public ResponseEntity<List<Person>> findByLastNameNot(@RequestParam  String lastname){
        List<Person> list = service.findByLastnameNot(lastname);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    

    @GetMapping("/person/findByAgeNotIn")
    public ResponseEntity<List<Person>> findByAgeNotIn(@RequestParam List<Integer> ages){
        List<Person> list = service.findByAgeNotIn(ages);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
        
}
