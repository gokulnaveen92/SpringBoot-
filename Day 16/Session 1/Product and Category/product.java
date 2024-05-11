1)//product
package com.example.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double price;

    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Product() {
    }

    public Product(String name, double price, Set<Category> categories) {
        this.name = name;
        this.price = price;
        this.categories = categories;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
        this.categories.add(categories);
    }

    


}


2)//repo
package com.example.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{


}

3)//interface
package com.example.springapp.service;

import com.example.springapp.model.Product;

public interface ProductService {

    Product addProduct(Product p , int categoryId);
    boolean delete(int productId);

}

4)//service
package com.example.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Category;
import com.example.springapp.model.Product;
import com.example.springapp.repository.CategoryRepository;
import com.example.springapp.repository.ProductRepository;

import jakarta.persistence.Id;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private CategoryRepository crepo;

    @Autowired
    private ProductRepository prepo;

    @Override
    public Product addProduct(Product p, int categoryId) {
       if(crepo.existsById(categoryId)){
        Category c = crepo.findById(categoryId).get();
        p.setCategories(c);;
        return prepo.save(p);
       }else{
        return null;
       }
    }

    @Override
    public boolean delete(int productId) {
        if(prepo.existsById(productId)){
            prepo.deleteById(productId);
            return true;
        }else{
            return false;
        }
    }

}

5)//controller
package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Product;
import com.example.springapp.service.ProductService;

@RestController
public class ProductController {


    @Autowired
    private ProductService pservice;

    @PostMapping("/categories/{categoryId}/product")
    public ResponseEntity<Product> addC(@PathVariable int categoryId,@RequestBody Product p){
        Product p1 = pservice.addProduct(p, categoryId);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Boolean> delete(@PathVariable int productId){
        boolean b = pservice.delete(productId);
        if(b){
            return new ResponseEntity<>(b,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(b,HttpStatus.OK);
        }
    }
    
}
