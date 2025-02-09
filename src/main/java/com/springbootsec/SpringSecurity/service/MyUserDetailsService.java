package com.springbootsec.SpringSecurity.service;

import com.springbootsec.SpringSecurity.model.User;
import com.springbootsec.SpringSecurity.model.UserPrincipal;
import com.springbootsec.SpringSecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= repo.findByUsername(username);// coming from database

        if(user==null){
            System.out.print("User not found");
            System.out.print(user);

            throw  new UsernameNotFoundException("User not found");
        }



        return  new UserPrincipal(user);

    }
}
