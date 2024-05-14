1)//auth contrfoller
package com.examly.springapp.controller;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class AuthController {
   
   @GetMapping("/auth/welcome")
   public ResponseEntity<String> welcome(){
       return new ResponseEntity<>("Welcome to this application",HttpStatus.OK);
   }
 
   @GetMapping("/auth/admin/profile")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public ResponseEntity<String> adminWelcome(){
       return new ResponseEntity<>("Welcome to Admin Profile",HttpStatus.OK);
   }
 
   @GetMapping("/auth/user/profile")
   @PreAuthorize("hasAuthority('ROLE_USER')")
   public ResponseEntity<String> userWelcome(){
       return new ResponseEntity<>("Welcome to User Profile",HttpStatus.OK);
   }
}

2)//security config
package com.examly.springapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails user = org.springframework.security.core.userdetails.User.withUsername("user")
        .password(encoder.encode("user123"))
        .roles("USER").build();

        UserDetails admin = org.springframework.security.core.userdetails.User.withUsername("admin")
        .password(encoder.encode("admin123"))
        .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user,admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/auth/welcome").permitAll()
        .requestMatchers("/auth/user/profile").authenticated()
        .requestMatchers("/auth/admin/profile").authenticated()
        .and().formLogin().and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder(); 
    }
    

}

