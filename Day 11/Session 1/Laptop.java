1)//laptop
package com.examly.springapp.model;

public class Laptop {
    private int laptopId;
    private String laptopBrand;
    private int laptopPrice;
    
    public Laptop(int laptopId, String laptopBrand, int laptopPrice) {
        this.laptopId = laptopId;
        this.laptopBrand = laptopBrand;
        this.laptopPrice = laptopPrice;
    }

    public int getLaptopId() {
        return laptopId;
    }

    public void setLaptopId(int laptopId) {
        this.laptopId = laptopId;
    }

    public String getLaptopBrand() {
        return laptopBrand;
    }

    public void setLaptopBrand(String laptopBrand) {
        this.laptopBrand = laptopBrand;
    }

    public int getLaptopPrice() {
        return laptopPrice;
    }

    public void setLaptopPrice(int laptopPrice) {
        this.laptopPrice = laptopPrice;
    }
   
}

2)//laptopcontroller
  package com.examly.springapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Laptop;
import com.examly.springapp.service.LaptopService;

@RestController
public class LaptopController {
    @Autowired
    private LaptopService service;

    @PostMapping("/laptop")
    public ResponseEntity<Laptop> addLaptop(@RequestBody Laptop lap ){
        Laptop lp = service.addLaptop(lap);
        if(lp!=null){
            return new ResponseEntity<>(lp,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        }

    @GetMapping("/laptop/{laptopId}")
    public ResponseEntity<Laptop> getByID(@PathVariable int laptopId){
        Laptop lp = service.byId(laptopId);
        if(lp!=null){
            return new ResponseEntity<>(lp,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/laptop")
    public ResponseEntity<List<Laptop>> getAllLaptops(){
        List<Laptop> list = service.getAll();

        if(list.size()>0){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

3)//laptopservice
  
package com.examly.springapp.service;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.stereotype.Service;
 
import com.examly.springapp.model.Laptop;
 
@Service
public class LaptopService {
    List<Laptop> list=new ArrayList<>();
   
    public Laptop addLaptop(Laptop lab){
        list.add(lab);
        return lab;
    }
 
    public Laptop byId(int id){
        for(Laptop lap:list){
            if( lap.getLaptopId() == id )
            return lap;
        }
        return null;
    }
 
    public List<Laptop> getAll(){
        return list;
    }
}
 
