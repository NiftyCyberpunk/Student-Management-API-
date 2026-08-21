package com.aryan.studentmanagementapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String name){

        User user = userRepository
            .findByUsername(name)
            .orElseThrow(() ->
                new UsernameNotFoundException("User with the username " + name + " not found")
            );

        
        
        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole().name())
            .disabled(!user.isEnabled())
            .build();

        return userDetails;
    }
}
