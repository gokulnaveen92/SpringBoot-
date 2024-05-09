package com.examly.springapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Student {
    
    @Id
    private long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "id")
    private StudentIdCard studentIDCard;
    
    public Student() {
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

    


    public Student(String name, StudentIdCard studentIDCard) {
        this.name = name;
        this.studentIDCard = studentIDCard;
    }

    public StudentIdCard getStudentIDCard() {
        return studentIDCard;
    }

    public void setStudentIDCard(StudentIdCard studentIDCard) {
        this.studentIDCard = studentIDCard;
    }       
}

2)//repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>{
    
}

3)//service
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Student;
import com.examly.springapp.repository.StudentIDRepository;
import com.examly.springapp.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public Student add(Student s){
        return repo.save(s);
    }

    public List<Student> getAll(){
        return repo.findAll();
    }

    public Student getById(long id){
        if(repo.existsById(id))
        return repo.findById(id).get();
        return null;
    }

    public Student updateStudent (long id,Student s){
        if(repo.existsById(id)){
            Student s2 = repo.findById(id).get();
            s2.setName(s.getName());
            s2.setStudentIDCard(s.getStudentIDCard());
            return s2;
        }else{
            return null;
        }
    }

    public boolean delete(long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

}

4)//controller
  package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Student;
import com.examly.springapp.service.StudentService;

@RestController
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/student")
    public ResponseEntity<Student> add(@RequestBody Student s){
        Student s1 = service.add(s);
        if(s1 != null){
            return new ResponseEntity<>(s1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student")
    public ResponseEntity<List<Student>> getAll(){
        List<Student> s2 = service.getAll();
        if(s2 != null){
            return new ResponseEntity<>(s2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getByID(@PathVariable long id){
        Student s3 = service.getById(id);
        if(s3 != null){
            return new ResponseEntity<>(s3,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @PutMapping("/student/{id}")
    public ResponseEntity<Student> update(@RequestBody Student s,@PathVariable long id){
        Student s4 = service.updateStudent(id, s);
        if(s4 != null){
            return new ResponseEntity<>(s4,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        boolean s4 = service.delete(id);
        if(s4){
            return new ResponseEntity<>("Deleted Student succesfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Student with ID "+id+" not found",HttpStatus.OK);
        }
    }
    
    
}


    


