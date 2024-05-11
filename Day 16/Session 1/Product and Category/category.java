1)//category
  package com.example.springapp.model;

import java.util.*;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    public Category(String name, Set<Product> products) {
        this.name = name;
        this.products = products;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void add(Product arg0) {
        this.products.add(arg0);
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}

2)//repo
  package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Category;


public interface CategoryRepository extends JpaRepository<Category,Integer>{

}


3)//interface
  package com.example.springapp.service;

import com.example.springapp.model.Category;

public interface CategoryService {

    Category addCategory(Category c);
    Category getById(int categoryId);
    


}

4)//service
  
  package com.example.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Category;
import com.example.springapp.repository.CategoryRepository;
import com.example.springapp.repository.ProductRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository crepo;

    @Autowired
    private ProductRepository prepo;

    @Override
    public Category addCategory(Category c) {
       return crepo.save(c);
    }

    @Override
    public Category getById(int categoryId) {
        if(crepo.existsById(categoryId)){
            return crepo.findById(categoryId).get();
        }else{
            return null;
        }
    }

}

5)//controller
  package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Category;
import com.example.springapp.service.CategoryService;


@RestController
public class CategoryController {

    @Autowired
    private CategoryService cservice;

    @PostMapping("/categories")
    public ResponseEntity<Category> add(@RequestBody Category c1){
        Category c = cservice.addCategory(c1);
        if(c!= null){
            return new ResponseEntity<>(c,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> get(@PathVariable int categoryId){
        Category c = cservice.getById(categoryId);
        if(c!= null){
            return new ResponseEntity<>(c,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
}


