package com.examly.springapp.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.examly.springapp.service.UserService;
import com.examly.springapp.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String pwd = null;
        List<GrantedAuthority> authorities = null;
        User usr = service.getByName(username);
        if(usr==null){
            throw new UsernameNotFoundException(pwd);
        }else{
            username = usr.getUserName();
            pwd = usr.getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(usr.getRole())); 
        }

        return new org.springframework.security.core.userdetails.User(username,pwd,authorities);

    }
}
