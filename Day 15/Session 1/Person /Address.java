1)//address
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String street;
    private String city;
    private String zipCode;

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public Address() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}

2)//repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{

}

3)//service
  package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Address;
import com.examly.springapp.model.Person;
import com.examly.springapp.repository.AddressRepository;
import com.examly.springapp.repository.PersonRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repo1;

    @Autowired
    private PersonRepository repo;

    public Address addAddress(long personId,Address address){
        if(repo.existsById(personId)){
            Person p = repo.findById(personId).get();
            Address adr = repo1.save(address);
            p.setAddress(adr);
            repo.save(p);
            return p.getAddress();
        
        }else{
            return null;
        }
    }



}

4)//controller
  package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Address;
import com.examly.springapp.service.AddressService;

@RestController
public class AddressController {

    @Autowired
    private AddressService service;

        @PostMapping("address/person/{personId}")
    public ResponseEntity<Address> addAddress(@RequestBody Address a,@PathVariable long personId){
        Address p1 = service.addAddress(personId, a);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

