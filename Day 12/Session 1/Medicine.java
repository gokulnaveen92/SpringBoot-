1)//Medicine
package com.example.springapp.model;


public class Medicine {
    private int medicineId,quantity;
    private String medicineName,description;
    private float price;
    
    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
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

    public Medicine(int medicineId, int quantity, String medicineName, String description, float price) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.medicineName = medicineName;
        this.description = description;
        this.price = price;
    }    
}

2)//service
  package com.example.springapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springapp.model.Medicine;

@Service
public class MedicineService {

    List<Medicine> list = new ArrayList<>();

    public Medicine addMed(Medicine m){
        list.add(m);
        return m;       
    }

    public Medicine updateMed(Medicine m ,int id){
        for(Medicine m1 : list){
            if(m1.getMedicineId()==id){
                m1.setMedicineName(m.getMedicineName());
                m1.setPrice(m.getPrice());
                m1.setQuantity(m.getQuantity());
                m1.setDescription(m.getDescription());
            }
            return m1;
        }
        return null;
    }
}

3)//controller
  package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.Medicine;
import com.example.springapp.service.MedicineService;

@RestController
public class MedicineController {

    @Autowired
    private MedicineService service;

    @PostMapping("/medicine")
    public ResponseEntity<Medicine> add(@RequestBody Medicine m){
        Medicine m1 = service.addMed(m);
        if(m1!=null){
        return new ResponseEntity<>(m1,HttpStatus.CREATED);
        }
        else{
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/medicine/{medicineId}")
    public ResponseEntity<Medicine> update(@RequestBody Medicine m , @PathVariable int medicineId){
        Medicine m2 = service.updateMed(m, medicineId);
        if(m2!=null){
            return new ResponseEntity<>(m2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

