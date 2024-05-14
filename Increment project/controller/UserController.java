package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.entity.User;
import com.examly.springapp.service.serviceImpl.UserServiceImpl;

@RestController
public class UserController {

    @Autowired
    UserServiceImpl serviceImpl;

    @PostMapping("api/user/register")
    public User userReg(@RequestBody User u){
        return serviceImpl.registerUser(u);
    }

    @PostMapping("api/user/login")
    public User userLogin(@RequestBody User u){
        return serviceImpl.loginUser(u);
    }

    @GetMapping("api/user")
    public List<User> getAllUsers(){
        return serviceImpl.getAllUsers();
    }
}
