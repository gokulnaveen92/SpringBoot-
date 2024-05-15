1)//user
package com.examly.springapp.model;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
 
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;
    public User() {
    }
 
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}

2)//user repo
package com.examly.springapp.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.examly.springapp.model.User;
 
public interface UserRepo extends JpaRepository<User,Integer>{
}

3)//service
package com.examly.springapp.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
 
@Service
public class UserService {
    @Autowired
    PasswordEncoder encoder;
 
    @Autowired
    private UserRepo urepo;
 
 
    public User register(User u){
        u.setPassword(encoder.encode(u.getPassword()));
        return urepo.save(u);
    }
}

4)//user controller
package com.examly.springapp.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;
 
@RestController
public class UserController {
    @Autowired
    private UserService service;
 
    @PostMapping("/api/register")
    public ResponseEntity <Boolean>  adddd(@RequestBody User u){
        User us=service.register(u);
        if(us!=null){
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

5)//security config
package com.examly.springapp.configuration;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    org.springframework.security.web.SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/api/register").permitAll()
        .and()
        .build();
    }
 
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

