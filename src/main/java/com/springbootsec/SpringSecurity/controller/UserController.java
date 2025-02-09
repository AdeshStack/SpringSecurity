package com.springbootsec.SpringSecurity.controller;


import com.springbootsec.SpringSecurity.model.User;
import com.springbootsec.SpringSecurity.repo.UserRepo;
import com.springbootsec.SpringSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping ("/register")
    public ResponseEntity<User> create(@RequestBody  User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public  ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userService.verify(user);
    }
}
