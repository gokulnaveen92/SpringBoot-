package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;

@Service
public class UserService {
    

    @Autowired
    private UserRepo uRepo;

    public String userReg(User u){
        uRepo.save(u);
        return "User saved successfully";
    }

    public String userLogin(User u){
        if(uRepo.existsById(u.getUserId()))
        return "Login Successful";
        return null;
    }

    public User getByName(String name){
        return uRepo.findByUserName(name);
    }
}
