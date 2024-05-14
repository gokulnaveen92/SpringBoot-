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

2)//security
package com.examly.springapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/auth/welcome").permitAll()
            .requestMatchers("/auth/admin/profile/").authenticated()
            .and()
            .formLogin()
            .and().build();
    }

}

