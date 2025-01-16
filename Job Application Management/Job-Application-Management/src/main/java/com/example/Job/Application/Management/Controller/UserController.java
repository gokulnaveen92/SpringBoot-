package com.example.Job.Application.Management.Controller;

import com.example.Job.Application.Management.DTO.AuthRequest;
import com.example.Job.Application.Management.Entity.User;
import com.example.Job.Application.Management.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        String status = userService.register(user);
        if(status.equals("\"message\" : \"User registered successfully.\""))
            return ResponseEntity.status(HttpStatus.CREATED).body(status);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(status);
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<String> updateProfile(@RequestBody User user,@PathVariable long userId){
        String status = userService.updateProfile(user,userId);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request){
        String token = userService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody AuthRequest request){
//        System.out.println("Login request received for user: " + request.getUsername()); // Add this line for logging
//        String token = userService.login(request.getUsername(), request.getPassword());
//        return ResponseEntity.ok(token);
//    }
}
