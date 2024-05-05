1)//shopingCart
package com.examly.springapp.model;

public class ShoppingCart {
    private long id;
    private String name;
    private double price;
    private int quantity;

    
    public ShoppingCart(long id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

2)//apiService
package com.examly.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.exception.ProductException;
import com.examly.springapp.model.ShoppingCart;

@Service
public class ApiService {
    List<ShoppingCart> list = new ArrayList<>();
    
    public String addProduct(ShoppingCart s) throws ProductException{
        for(ShoppingCart s1:list){
            if(s1.getName().equals(s.getName())){
                throw new ProductException("Invalid product details");
            }
        }
        list.add(s);
        return "Successfully added the product to the cart";
    }
    
    public String getTotal(){
        double totalprice = 0;
        for(ShoppingCart s2 : list){
            totalprice = totalprice + (s2.getPrice()*s2.getQuantity());
        }
        return "Successfully retrieved the total price of the cart: "+totalprice;
    }

   
}

3)//productException
package com.examly.springapp.exception;


public class ProductException extends Exception{
    public ProductException(String msg){
        super(msg);
    }
}

4)//exceptionHnadler
package com.examly.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<String> exceptionHandler(ProductException pe){
        return new ResponseEntity<>(pe.getMessage(),HttpStatus.BAD_REQUEST);
    }
}

5)//controller
package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exception.ProductException;
import com.examly.springapp.model.ShoppingCart;
import com.examly.springapp.service.ApiService;

@RestController
public class CartController {

    @Autowired
    private ApiService service;

    @PostMapping("/api/product")
    public ResponseEntity<String> addP(@RequestBody ShoppingCart s) throws ProductException{
        String s1 = service.addProduct(s);
        return new ResponseEntity<>(s1,HttpStatus.CREATED);
    }

    @GetMapping("/api/product")
    public ResponseEntity<String> getP(){
        String s2 = service.getTotal();
        return new ResponseEntity<>(s2,HttpStatus.OK);
    }
}
