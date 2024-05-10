1))//product
package com.examly.springapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;
    private String productName;
    private double price;
    
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;

    public Product() {
    }

    public Product(String productName, double price, Customer customer) {
        this.productName = productName;
        this.price = price;
        this.customer = customer;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}

2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{
    
}

3)//interface
package com.examly.springapp.service;

import com.examly.springapp.model.Product;

public interface ProductService {

    public Product addProduct(Product product,int customerId);
    public boolean deleteProduct(int productId);
    

}

4))//service
package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Customer;
import com.examly.springapp.model.Product;
import com.examly.springapp.repository.CustomerRepo;
import com.examly.springapp.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepo prepo;

    @Autowired
    private CustomerRepo crepo;

    
    @Override
    public Product addProduct(Product product, int customerId) {
        if(crepo.existsById(customerId)){
            Customer c = crepo.findById(customerId).get();
            product.setCustomer(c);
            return prepo.save(product);
        }else{
            return null;
        }
    }


    @Override
    public boolean deleteProduct(int productId) {
        if(prepo.existsById(productId)){
            prepo.deleteById(productId);
            return true;
        }else{
            return false;
        }
    }


    
}

5))//controller

package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Product;
import com.examly.springapp.service.ProductServiceImpl;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @PostMapping("/product/customer/{customerId}")
    public ResponseEntity<Product> add(@PathVariable int customerId,@RequestBody Product p){
        Product p1 = service.addProduct(p, customerId);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> delete(@PathVariable int productId){
        boolean b = service.deleteProduct(productId);
        if(b){
            return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product not found with ID "+productId,HttpStatus.NOT_FOUND);
        }
    }
    
}
