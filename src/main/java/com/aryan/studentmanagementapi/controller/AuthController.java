package com.aryan.studentmanagementapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.studentmanagementapi.dto.AuthResponseDTO;
import com.aryan.studentmanagementapi.dto.LoginRequestDTO;
import com.aryan.studentmanagementapi.dto.RefreshTokenRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentRegisterRequestDTO;
import com.aryan.studentmanagementapi.service.AuthService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register/student")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDTO registerStudent(@Valid @RequestBody StudentRegisterRequestDTO studentRegisterRequest){

        return authService.registerStudent(studentRegisterRequest);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest) {

        authService.logoutUser(refreshTokenRequest);
    }

    @PostMapping("/refresh")
    public AuthResponseDTO refreshTokens(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
       
        return authService.refreshTokens(refreshTokenRequest);
    }
}