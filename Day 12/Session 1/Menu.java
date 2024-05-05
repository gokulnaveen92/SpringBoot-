1)//Menu
package com.example.springapp.model;

public class MenuItem {
    private int menuId,quantity;
    private String itemName,description;
    private float price;

    public MenuItem(int menuId, int quantity, String itemName, String description, float price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }   
}

2)//service
  package com.example.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springapp.model.MenuItem;

@Service
public class MenuService {

    List<MenuItem> list = new ArrayList<>();

    public MenuItem addMenu (MenuItem m){
        list.add(m);
        return m;     

    }

    public String delMenu ( int id){
        for (MenuItem m1 : list){
            if(m1.getMenuId()==id){
                list.remove(m1);
                return "Menu item deleted successfully";
            }
        }
        return "Menu item not found with the specified menuId";
    }

    
}

3)//controller
  package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.MenuItem;
import com.example.springapp.service.MenuService;

@RestController
public class MenuController {
    
    @Autowired
    private MenuService service;

    @PostMapping("/menu")
    public ResponseEntity<MenuItem> add(@RequestBody MenuItem m ){
        MenuItem m1 = service.addMenu(m);
        if(m1!=null){
        return new ResponseEntity<>(m1,HttpStatus.CREATED);
        }
        else{
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("menu/{menuId}")
    public ResponseEntity<String> update(@PathVariable int menuId){
        String m2 = service.delMenu(menuId);
      
        return new ResponseEntity<>(m2,HttpStatus.OK);

    }
}

