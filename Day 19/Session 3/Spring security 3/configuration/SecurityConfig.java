package com.examly.springapp.configuration;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
   
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
       return http.csrf().disable()
       .authorizeHttpRequests()
       .requestMatchers("/user/register","/user/login").permitAll()
       .requestMatchers(HttpMethod.POST,"/account").hasRole(("USER"))
       .requestMatchers(HttpMethod.GET,"/account").hasRole("ADMIN")
       .requestMatchers(HttpMethod.DELETE,"/account/{accountId}").hasRole("ADMIN")
       .and()
       .formLogin()
       .and()
       .build();
   }
 
   @Bean
   public UserDetailsService detailsService(){
       return new CustomUserDetailsService();
   }
 
   @Bean
   public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }
}
