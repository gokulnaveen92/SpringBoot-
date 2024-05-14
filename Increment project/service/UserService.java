package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.entity.User;

public interface UserService {

    User registerUser(User user);
    List<User> getAllUsers();
    User loginUser(User user);

}
