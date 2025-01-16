package com.example.Job.Application.Management.Service;

import com.example.Job.Application.Management.Configuration.JwtUtil;
import com.example.Job.Application.Management.Entity.User;
import com.example.Job.Application.Management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String register(User user) {
        Optional<User> existUser = userRepository.findByUsername(user.getUsername());
        if(existUser.isPresent()){
            return "\"message\" : \"Username already exists.\"";
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getRole() == null)
                user.setRole(("ROLE_USER"));
            userRepository.save(user);
            return "\"message\" : \"User registered successfully.\"";
        }

    }

    @Override
    public String updateProfile(User user,long userId) {
        User existUser = userRepository.findByUserId(userId);
        existUser.setFullName(user.getFullName());
        existUser.setEmail(user.getEmail());
        existUser.setPhone(user.getPhone());
        existUser.setSkills(user.getSkills());
        userRepository.save(existUser);
        return "\"message\": \"Profile updated successfully.\"";
    }

    @Override
    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        return jwtUtil.generateToken(username);
    }
//
//    @Override
//    public String login(String username, String password) {
//        try {
//            System.out.println("Attempting authentication for user: " + username); // Add this line for logging
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            System.out.println("Authentication successful for user: " + username); // Add this line for logging
//            return jwtUtil.generateToken(username);
//        } catch (Exception e) {
//            System.out.println("Authentication failed for user: " + username); // Add this line for logging
//            throw e;
//        }
//    }
}
