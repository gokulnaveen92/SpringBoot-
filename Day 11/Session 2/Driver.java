1))//driver
package com.examly.springapp.model;

public class Driver {
    private int driverId;
    private String name;
    private String licenseNumber;

    public Driver(int driverId, String name, String licenseNumber) {
        this.driverId = driverId;
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }  
    
}

2)//service
package com.examly.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Driver;

@Service
public class DriverService {

    List<Driver> list = new ArrayList<>();

    public Driver addDriver(Driver d){
        list.add(d);
        return d;
    }

    public Driver getId(int id){
        for(Driver d1 : list){
            if(d1.getDriverId()==id){
                return d1;
            }
        }
        return null;
    }

    public List<Driver> getAll(){
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

import com.examly.springapp.model.Driver;
import com.examly.springapp.service.DriverService;

@RestController
public class DriverController {

    @Autowired
    private DriverService service;

    @PostMapping("/driver")
    public ResponseEntity<Driver> add(@RequestBody Driver p){
        Driver d = service.addDriver(p);
        if(d!=null){
            return new ResponseEntity<>(d,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/driver")
    public ResponseEntity<List<Driver>> getAllDriver(){
        List<Driver> list = service.getAll();
        if(!list.isEmpty()){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Driver> getByDriver(@PathVariable int driverId){
        Driver d1 = service.getId(driverId);
        if(d1!=null){
            return new ResponseEntity<>(d1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

