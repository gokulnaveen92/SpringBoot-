1))//customer
package com.examly.springapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId;
    private String customerName;
    private String address;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private List<Product> products ;

    public Customer() {
    }

    public Customer(String customerName, String address, List<Product> products) {
        this.customerName = customerName;
        this.address = address;
        this.products = products;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products.add(products);
    }    
}

2)//repo
  package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {

}

3)//interface
  
package com.examly.springapp.service;

import com.examly.springapp.model.Customer;

public interface CustomerService {

    public Customer add(Customer customer);
    public Customer getById(int customerId);

}

4)//service
package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Customer;
import com.examly.springapp.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepo repo;

    @Override
    public Customer add(Customer customer) {
        return repo.save(customer);
    }

    @Override
    public Customer getById(int customerId) {
        if(repo.existsById(customerId)){
            return repo.findById(customerId).get();
        }else{
            return null;
        }

    }

}

5))//controller
package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Customer;
import com.examly.springapp.service.CustomerServiceImpl;

@RestController
public class CustomerController {

    @Autowired
    private CustomerServiceImpl service;

    @PostMapping("/customer")
    public ResponseEntity<Customer> add(@RequestBody Customer c){
        Customer c1 = service.add(c);
        if(c1!=null){
            return new ResponseEntity<>(c1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getById(@PathVariable int customerId){
        Customer c = service.getById(customerId);
        if(c != null){
            return new ResponseEntity<>(c,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
