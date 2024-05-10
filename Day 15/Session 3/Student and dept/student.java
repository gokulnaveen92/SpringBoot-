1)//student
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Student {

    @Id
    private int studentId;
    private String name;
    private String email;
    private int age;

    
    @ManyToOne
    private Department department;

    public Student() {
    }
    
    public Student(int studentId, String name, String email, int age, Department department) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.department = department;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    

}


2))//repo
package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Department;
import com.examly.springapp.model.Student;

@Repository
public interface StudenrRepo extends JpaRepository<Student,Integer> {


    public List<Student> findByDepartment(Department departmentId);
}

3//service
  package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Department;
import com.examly.springapp.model.Student;
import com.examly.springapp.repository.DeaprtmentRepo;
import com.examly.springapp.repository.StudenrRepo;

@Service
public class StudentService {

    @Autowired
    private StudenrRepo srepo;
    
    @Autowired
    private DeaprtmentRepo drepo;

    public Student addStudent(Student s , int departmentId){
        if(drepo.existsById(departmentId)){
            Department d = drepo.findById(departmentId).get();
            Student s1 = srepo.save(s);
            s1.setDepartment(d);
            return s1;  
        }else{
            return null;
        }
    }

    public List<Student> studentByDept(int departmentId){
        if(drepo.existsById(departmentId)){
            return srepo.findByDepartment(drepo.findById(departmentId).get());
        }else{
            return null;
        }
    }
    
    public List<Student> getAllStudents(){
        return srepo.findAll();
    }

    public Student getByStuId(int studentId){
        return srepo.findById(studentId).get();
    }

}

4)//controller
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

import com.examly.springapp.model.Department;
import com.examly.springapp.model.Student;
import com.examly.springapp.service.StudentService;

@RestController
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/department/{departmentId}/student")
        public ResponseEntity<Student> addDept(@RequestBody Student d,@PathVariable int departmentId){
        Student d1 = service.addStudent(d, departmentId);
        if(d1 != null){
            return new ResponseEntity<>(d1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/department/{departmentId}/student")
    public ResponseEntity<List<Student>> byDeptId(@PathVariable int departmentId){
    List<Student> d3 = service.studentByDept(departmentId);
        if(d3 != null){
            return new ResponseEntity<>(d3,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/department/student")
    public ResponseEntity<List<Student>> getAll(){
    List<Student> d4 = service.getAllStudents();
        if(d4 != null){
            return new ResponseEntity<>(d4,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/department/student/{studentId}")
    public ResponseEntity<Student> getByStuId(@PathVariable int studentId){
        Student d5 = service.getByStuId(studentId);
        if(d5 != null){
            return new ResponseEntity<>(d5,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

  
