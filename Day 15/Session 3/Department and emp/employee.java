1)//department
package com.examly.springapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int employeeId;
    private String employeeName;
    private String designation;

    @JsonBackReference
    @ManyToOne
    private Department department;

    public Employee() {
    }

    public Employee(String employeeName, String designation, Department department) {
        this.employeeName = employeeName;
        this.designation = designation;
        this.department = department;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}


2)//repo
package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer>{

}

3)//interface
package com.examly.springapp.service;

import com.examly.springapp.model.Employee;

public interface EmployeeService {

    Employee addEmp(Employee e,int departmentId);
    Employee update(Employee e,int employeeId);
    boolean delete(int employeeId);

}

4)//service
package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Department;
import com.examly.springapp.model.Employee;
import com.examly.springapp.repository.DepartmentRepo;
import com.examly.springapp.repository.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepo erepo;;

    @Autowired
    private DepartmentRepo dRepo;

    @Override
    public Employee addEmp(Employee e, int departmentId) {
        if(dRepo.existsById(departmentId)){
            Department d = dRepo.findById(departmentId).get();
            e.setDepartment(d);
            return erepo.save(e);
        }else{
            return null;
        }
    }

    @Override
    public Employee update(Employee e ,int employeeId) {
        if(erepo.existsById(employeeId)){
            e.setEmployeeId(employeeId);
            return erepo.save(e);
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(int employeeId) {
        if(erepo.existsById(employeeId)){
            erepo.deleteById(employeeId);
            return true;
        }else{
            return false;
        }
    }

}


5)//controller
package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Employee;
import com.examly.springapp.service.EmployeeService;


@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/department/employee/{departmentId}")
    public ResponseEntity<Employee> add(@RequestBody Employee e,@PathVariable int departmentId){
        Employee e1 = service.addEmp(e, departmentId);
        if(e1 != null){
            return new ResponseEntity<>(e1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 
    
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<Employee> up(@RequestBody Employee e,@PathVariable int employeeId){
        Employee e1 = service.update(e, employeeId);
        if(e1 != null){
            return new ResponseEntity<>(e1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } 
    
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<String> del(@PathVariable int employeeId){
        boolean e1 = service.delete(employeeId);
        if(e1){
            return new ResponseEntity<>("Employee deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Employee not found with ID: "+employeeId,HttpStatus.OK);
         }
    } 

}
