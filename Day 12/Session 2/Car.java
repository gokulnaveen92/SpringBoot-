1)//car
package com.example.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Car {

    @Id
    private int carId;
    private String brand;
    private String color;
    private int price;

    
    public Car() {
    }

    public Car(int carId, String brand, String color, int price) {
        this.carId = carId;
        this.brand = brand;
        this.color = color;
        this.price = price;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

2)//repository
  package com.example.springapp.repository;

import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapp.model.Car;

@Repository
public interface CarRepo extends JpaRepository<Car,Integer> {

}

3)//service
  package com.example.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springapp.model.Car;
import com.example.springapp.repository.CarRepo;

@Service
public class CarService {

    @Autowired
    private CarRepo repo;

    public Car addCar(Car c){
        return repo.save(c);
    }

    public Car updateCar(Car c , int id){
        if(repo.existsById(id)){
            Car c1 = repo.findById(id).get();
            c1.setBrand(c.getBrand());
            c1.setColor(c.getColor());
            c1.setPrice(c.getPrice());
            return c1;
        }else{
            return null;
        }
    }

    public boolean delCar(int id){
        repo.deleteById(id);
        if(repo.existsById(id)){
            return false;
        }else{
            return true;
        }
    }
}


4)//controller
  package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Car;
import com.example.springapp.service.CarService;

@RestController
public class CarController {

    @Autowired
    private CarService service;

    @PostMapping("/car")
    public ResponseEntity<Car> add(@RequestBody Car c){
        Car c1 = service.addCar(c);
        if(c1!=null){
            return new ResponseEntity<>(c1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/car/{carId}")
    public ResponseEntity<Car> update(@RequestBody Car c,@PathVariable int carId){
        Car c2 = service.updateCar(c, carId);
        if(c2!=null){
            return new ResponseEntity<>(c2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    @DeleteMapping("/car/{carId}")
    public ResponseEntity<Boolean> del(@PathVariable int carId){
        Boolean b1 = service.delCar(carId);
        if(b1.booleanValue()){
            return new ResponseEntity<>(b1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(b1,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}

