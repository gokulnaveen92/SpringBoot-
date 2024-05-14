package com.examly.springapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/auth/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Welcome to this application",HttpStatus.OK);
    }

    @GetMapping("/auth/admin/profile")
    public ResponseEntity<String> welcomeAdmin(){
        return new ResponseEntity<>("Welcome to Admin Profile",HttpStatus.OK);
    }
}
