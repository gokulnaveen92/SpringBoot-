1)//product
package com.example.springapp.model;

public class Product {
    private long productId;
    private String productName;
    private double price;
    private int quantity;

    public Product(long productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }   
}

2)//service
  package com.example.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springapp.model.Product;

@Service
public class ProductService {
    List<Product> list = new ArrayList<>();

    public Product addProduct(Product p){
        list.add(p);
        return p;
    }

    public Product getId(long id){
        for(Product p1 : list){
            if(p1.getProductId()==id){
                return p1;
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

import com.example.springapp.model.Product;
import com.example.springapp.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/products")
    public ResponseEntity<Product> addP(@RequestBody Product p){
        Product p1 = service.addProduct(p);
        if(p1!=null){
            return new ResponseEntity<>(p1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getP(@PathVariable long id){
        Product p2 = service.getId(id);
        if(p2!=null){
            return new ResponseEntity<>(p2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

