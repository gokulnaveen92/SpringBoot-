1))//dept
package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Department {
    
    @Id
    private int departmentId;
    private String departmentName;
    private String departmentEmail;
    private String headOfDepartment;
    
    public Department() {
    }
    
    public Department(String departmentName, String departmentEmail, String headOfDepartment) {
        this.departmentName = departmentName;
        this.departmentEmail = departmentEmail;
        this.headOfDepartment = headOfDepartment;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departentId) {
        this.departmentId = departentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departentName) {
        this.departmentName = departentName;
    }

    public String getDepartmentEmail() {
        return departmentEmail;
    }

    public void setDepartmentEmail(String departmentEmail) {
        this.departmentEmail = departmentEmail;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }
}

2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.examly.springapp.model.Department;
import com.examly.springapp.model.Student;

@Repository
public interface DeaprtmentRepo extends JpaRepository<Department,Integer> {

    

}

3)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.examly.springapp.model.Department;
import com.examly.springapp.model.Student;

@Repository
public interface DeaprtmentRepo extends JpaRepository<Department,Integer> {

    

}

4)//service
package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Department;
import com.examly.springapp.repository.DeaprtmentRepo;

@Service
public class DepartmentService {

    @Autowired
    private DeaprtmentRepo drepo;

    public Department addDept(Department d){
        return drepo.save(d);
    }
    public List<Department> getAll(){
        return drepo.findAll();
    }


}

5)//controller
package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Department;
import com.examly.springapp.service.DepartmentService;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @PostMapping("/department")
    public ResponseEntity<Department> addDept(@RequestBody Department d){
        Department d1 = service.addDept(d);
        if(d1 != null){
            return new ResponseEntity<>(d1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/department")
    public ResponseEntity<List<Department>> getAll(){
        List<Department> d2 = service.getAll();
        if(d2 != null){
            return new ResponseEntity<>(d2,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    

}
