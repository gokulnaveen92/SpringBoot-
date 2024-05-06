1)//product
package com.example.springapp.model;

public class Product {
    private long id;
    private String name;
    private double price;

    public Product(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }  

}

2)//service
package com.example.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springapp.exception.ProductException;
import com.example.springapp.model.Product;

@Service
public class ProductService {

    List<Product> list = new ArrayList<>();

    public String addProduct(Product p) throws ProductException{
        for(Product p1 : list){
        if(p1.equals(p)){
            throw new ProductException("Invalid product details");
        }
        }
    list.add(p);
    return "Successfully added the product.";
    }

    public Product getId(long id){
        for(Product p2 : list){
            if(p2.getId()==id){
                return p2;
            }
        }
        return null;
    }
}

3)//controller
  package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.exception.ProductException;
import com.example.springapp.model.Product;
import com.example.springapp.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/api/product")
    public ResponseEntity<String> addP(@RequestBody Product p) throws ProductException {
        String s1 = service.addProduct(p);
        return new ResponseEntity<>(s1,HttpStatus.CREATED);
    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable long id) {
        Product p1 = service.getId(id);  
        if(p1!=null){      
            return new ResponseEntity<>(p1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}

4)//exception
  package com.example.springapp.exception;

public class ProductException extends Exception {
    public ProductException(String msg){
        super(msg);
    }
}

5)//exception handler
  package com.example.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<String> exceptionHandler(ProductException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}

