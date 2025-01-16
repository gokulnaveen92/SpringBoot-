package com.example.Job.Application.Management.Service;

import com.example.Job.Application.Management.Entity.User;

public interface UserService {

    public String register(User user);
    public String updateProfile(User user,long userId);
    public String login(String username,String password);
}
