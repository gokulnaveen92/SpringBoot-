1)Account controller 
package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Account;
import com.examly.springapp.service.AccountService;

@RestController
public class AccountController {

    @Autowired 
    private AccountService service;

    @PostMapping("/account")
    public ResponseEntity<Account> addUser(@RequestBody Account u){
        Account u1 = service.addNew(u);
        if(u1 != null){
            return new ResponseEntity<>(u1,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAllUsers(){
        List<Account> u1 = service.getAll();
        if(u1 != null){
            return new ResponseEntity<>(u1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<Boolean> delete(@PathVariable int accountId){
        boolean u1 = service.deleteUser(accountId);
        if(u1){
            return new ResponseEntity<>(u1,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }    
}

2)//user controller
package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.configuration.CustomUserDetailsService;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired 
    private CustomUserDetailsService uService;

    @PostMapping("/user/register")
    public ResponseEntity<String> userReg(@RequestBody User u){
        String s = service.userReg(u);
        if(s != null){
            return new ResponseEntity<>(s,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/user/login")
    public ResponseEntity<String> userLogin(@RequestBody User u){
        UserDetails u1 = uService.loadUserByUsername(u.getUserName());
        if(u1 != null){
            return new ResponseEntity<>("Login Successful",HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
