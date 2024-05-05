1)//item
package com.example.springapp.model;

public class Item {
    private long id;
    private String name;
    private double price;
    private int quantity;

    public Item(long id, String name, double price, int quantity) {
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

2)//service
package com.example.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springapp.exception.ItemException;
import com.example.springapp.model.Item;

@Service
public class InventoryService {
    List<Item> list = new ArrayList<>();

    public String addItem(Item i) throws ItemException{
        for(Item i1 : list){
            if(i1.getName().equals(i.getName())){
                throw new ItemException("Invalid item details");
            }
        }
        list.add(i);
        return "Successfully added the item to the inventory";
    }

    public String getTotal(){
        double total = 0;
        for(Item i2 : list){
            total = total + (i2.getPrice()*i2.getQuantity());
        }
        return "Successfully retrieved the total value of the inventory: "+total;
    }
}

3)//exception
package com.example.springapp.exception;

public class ItemException extends Exception{

    public ItemException(String string) {
      super(string);
    }

}

4)//exception handler
package com.example.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ItemExceptionHandler {

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<String> exceptionHandler(ItemException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}

5)//controller
package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.exception.ItemException;
import com.example.springapp.model.Item;
import com.example.springapp.service.InventoryService;

@RestController
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping("/api/item")
    public ResponseEntity<String> addI(@RequestBody Item i) throws ItemException{
        String s1 = service.addItem(i);
        return new ResponseEntity<>(s1,HttpStatus.CREATED);
    }

    @GetMapping("/api/item")
    public ResponseEntity<String> getT(){
        String s2 = service.getTotal();
        return new ResponseEntity<>(s2,HttpStatus.OK);
    }
}
