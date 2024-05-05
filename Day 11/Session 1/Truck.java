1)//truck
package com.examly.springapp.model;

public class Truck {
    private int truckId;
    private String truckCapacity;
    private String truckColor;
    private String gearType;

    

    public Truck(int truckId, String truckCapacity, String truckColor, String gearType) {
        this.truckId = truckId;
        this.truckCapacity = truckCapacity;
        this.truckColor = truckColor;
        this.gearType = gearType;
    }

    public int getTruckId() {
        return truckId;
    }
    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }
    public String getTruckCapacity() {
        return truckCapacity;
    }
    public void setTruckCapacity(String truckCapacity) {
        this.truckCapacity = truckCapacity;
    }
    public String gettruckColor() {
        return truckColor;
    }
    public void settruckColor(String truckColor) {
        this.truckColor = truckColor;
    }
    public String getGearType() {
        return gearType;
    }
    public void setGearType(String gearType) {
        this.gearType = gearType;
    }
}

2)//truckService
package com.examly.springapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Truck;

import java.util.ArrayList;

@Service
public class TruckService {

    List<Truck> list = new ArrayList<>();

    public Truck addTruck(Truck truck){
        list.add(truck);
        return truck;
    }

    public Truck byId(int id){
        for(Truck t : list){
            if(t.getTruckId()==id){
                return t;
            }
        }
        return null;
    }

    public List<Truck> getAll(){
        return list;
    }

}

3)//controller
package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Truck;
import com.examly.springapp.service.TruckService;



@RestController
public class TruckController {
    
    @Autowired
    private TruckService service;

    @PostMapping("/truck")
    public ResponseEntity<Truck> addTruck(@RequestBody Truck t){
    Truck t1 = service.addTruck(t);
    if(t!=null){
        return new ResponseEntity<>(t1,HttpStatus.OK);
    }else{
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/truck/{truckId}")
    public ResponseEntity<Truck> getById(@PathVariable int truckId){
        Truck t = service.byId(truckId);
        if(t!=null){
            return new ResponseEntity<>(t,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/truck")
    public ResponseEntity<List<Truck>> getAll(){
        List<Truck> list = service.getAll();
        if(list.size()>0){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
