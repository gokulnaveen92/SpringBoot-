1)//person
package com.examly.springapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    
    
    @OneToOne
    private Passport passport;
    
    public Person() {
    }
    
    
    public Person(String name, String dateOfBirth, String email, String phoneNumber, Passport passport) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passport = passport;
    }
    
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}

2)//repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long>{

}

3)//service
  package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Passport;
import com.examly.springapp.model.Person;
import com.examly.springapp.repository.PassportRepository;
import com.examly.springapp.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repo2;

    @Autowired
    private PassportRepository repo;

    public Person addPerson(Person p,long passportId){
        if(repo.existsById(passportId)){
            Passport p2 = repo.findById(passportId).get();
            p.setPassport(p2);
            Person p1 = repo2.save(p);
            return p1;
        }else{
            return null;
        }   
    }


    public Person getById(long personId){
        return repo2.findById(personId).get();
    }
}

4)//controller
  package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Passport;
import com.examly.springapp.model.Person;
import com.examly.springapp.repository.PassportRepository;
import com.examly.springapp.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repo2;

    @Autowired
    private PassportRepository repo;

    public Person addPerson(Person p,long passportId){
        if(repo.existsById(passportId)){
            Passport p2 = repo.findById(passportId).get();
            p.setPassport(p2);
            Person p1 = repo2.save(p);
            return p1;
        }else{
            return null;
        }   
    }


    public Person getById(long personId){
        return repo2.findById(personId).get();
    }



}


