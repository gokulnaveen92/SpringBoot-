1)//doctor
package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String specialization;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "doctor_patient",
        joinColumns = @JoinColumn(name = "doctor_id"),
        inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patients> patients = new HashSet<>();

    public Doctor() {
    }

    public Doctor(String name, String specialization, Set<Patients> patients) {
        this.name = name;
        this.specialization = specialization;
        this.patients = patients;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Set<Patients> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patients> patients) {
        this.patients = patients;
    }

    public void add(Patients arg0) {
        patients.add(arg0);
    }
}

2)//patient
package com.examly.springapp.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @ManyToMany(mappedBy = "patients",cascade = CascadeType.ALL)
    private Set<Doctor> doctors = new HashSet<>();

    public Patients() {
    }

    public Patients(String name, Set<Doctor> doctors) {
        this.name = name;
        this.doctors = doctors;
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

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public  void add(Doctor arg0) {
        doctors.add(arg0);
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    
    
}

3)//doctorRepo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long>{

}

4)//patientRepo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Patients;

@Repository
public interface PatientRepository extends JpaRepository<Patients,Long>{

}

5)//api service
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Doctor;
import com.examly.springapp.model.Patients;
import com.examly.springapp.repository.DoctorRepository;
import com.examly.springapp.repository.PatientRepository;

@Service
public class ApiService {

    @Autowired
    private DoctorRepository drepo;

    @Autowired
    private PatientRepository prepo;

    public Doctor addDoctor(Doctor d){
        return drepo.save(d);
    }

    public Patients addPatient(Patients p){
        return prepo.save(p);
    }

    public Doctor addPatientToDoctor(long doctorId,long patientId){
        if(drepo.existsById(doctorId) && prepo.existsById(patientId)){
            Doctor d = drepo.findById(doctorId).get();
            Patients p = prepo.findById(patientId).get();
            d.add(p);
            return drepo.save(d);
        }else{
            return null;
        }
    }

    public List<Doctor> getAllDoctors(){
        return drepo.findAll();
    }

    public List<Patients> getAllPatients(){
        return prepo.findAll();
    }

    public boolean delete(long doctorId){
        if(drepo.existsById(doctorId)){
            drepo.deleteById(doctorId);
            return true;
        }else{
            return false;
        }
    }
}


6)//api controller
  
  package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.examly.springapp.model.Doctor;
import com.examly.springapp.model.Patients;
import com.examly.springapp.service.ApiService;

@RestController
public class ApiController {
    
    @Autowired
    private ApiService service;

    @PostMapping("api/doctors")
    public ResponseEntity<Doctor> addD(@RequestBody Doctor d){
        Doctor d1 = service.addDoctor(d);
        if(d1 != null){
            return new ResponseEntity<>(d1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/patients")
    public ResponseEntity<Patients> addP(@RequestBody Patients p){
        Patients p1 = service.addPatient(p);
        if(p1 != null){
            return new ResponseEntity<>(p1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/doctors/{doctorId}/add-patient/{patientId}")
    public ResponseEntity<Doctor> addPatientToDoctor(@PathVariable long doctorId,@PathVariable long patientId){
        Doctor d = service.addPatientToDoctor(doctorId, patientId);
        if(d != null){
            return new ResponseEntity<>(d ,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/patients")
    public ResponseEntity<List<Patients>> getListP(){
        List<Patients> list = service.getAllPatients();
        if(list != null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/doctors")
    public ResponseEntity<List<Doctor>> getListD(){
        List<Doctor> list = service.getAllDoctors();
        if(list != null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/doctors/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        boolean b = service.delete(id);
        if(b){
            return new ResponseEntity<>(b,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(b,HttpStatus.NOT_FOUND);
        }
    }
}

  
