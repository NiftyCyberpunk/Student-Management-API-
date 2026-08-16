package com.aryan.studentmanagementapi.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.studentmanagementapi.dto.LoginRequestDTO;
import com.aryan.studentmanagementapi.dto.RegisterRequestDTO;
import com.aryan.studentmanagementapi.service.AuthService;
import com.aryan.studentmanagementapi.service.JwtService;

@RestController
public class AuthController {
    
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody LoginRequestDTO loginRequest) {
        
        Authentication authentication = 
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );
        

            return jwtService.generateToken(authentication.getName());
    }

    @PostMapping("/auth/register")
    public String register(@RequestBody RegisterRequestDTO registerRequest) {

        return authService.registerUser(registerRequest);
    }
}
