1)//department
package com.examly.springapp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int departmentId;
    private String departmentName;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    public Department( String departmentName, List<Employee> employees) {
        this.departmentName = departmentName;
        this.employees = employees;
    }

    public Department() {
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setList(List<Employee> employees) {
        this.employees = employees;
    }

    public void addList(Employee employee){
        this.employees.add(employee);
    }

    

}


2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Integer>{



}

3)//interface
package com.examly.springapp.service;

import java.util.List;
import com.examly.springapp.model.Department;

public interface DepartmentService{

    Department addDept(Department d);
    List<Department> getAll();
    
    

}

4)//service
package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Department;
import com.examly.springapp.repository.DepartmentRepo;
import com.examly.springapp.repository.EmployeeRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepo drepo;

    @Autowired
    private EmployeeRepo erepo;


    @Override
    public Department addDept(Department d) {
        return drepo.save(d);
    }

    @Override
    public List<Department> getAll() {
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Department;
import com.examly.springapp.model.Employee;
import com.examly.springapp.repository.DepartmentRepo;
import com.examly.springapp.service.DepartmentService;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @PostMapping("/department")
    public ResponseEntity<Department> add(@RequestBody Department d){
        Department e1 = service.addDept(d);
        if(e1 != null){
            return new ResponseEntity<>(e1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/department")
    public ResponseEntity<List<Department>> getAllDept(){
        List<Department> e1 = service.getAll();
        if(e1 != null){
            return new ResponseEntity<>(e1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

}
