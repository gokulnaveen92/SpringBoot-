package com.examly.springapp.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.entity.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo uRepo;

@Override
public User registerUser(User user) {
    return uRepo.save(user);
}

@Override
public List<User> getAllUsers() {
    return uRepo.findAll();
}

@Override
public User loginUser(User user) {
    if(uRepo.existsById(user.getUserId())){
        return user;
    }else{
        return null;
    }
}

}
