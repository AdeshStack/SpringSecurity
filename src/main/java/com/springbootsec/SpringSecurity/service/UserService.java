package com.springbootsec.SpringSecurity.service;

import com.springbootsec.SpringSecurity.model.User;
import com.springbootsec.SpringSecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private  JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    public User create(User user){
        return repo.save(user);
    }
    public List<User> getAll(){
        return repo.findAll();
    }

    public String verify(User user){

        Authentication authentication=
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){

            return  jwtService.generateToken(user.getUsername());

        }

        return "fail";
    }

}
