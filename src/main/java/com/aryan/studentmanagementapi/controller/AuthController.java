package com.aryan.studentmanagementapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.studentmanagementapi.dto.AuthResponseDTO;
import com.aryan.studentmanagementapi.dto.LoginRequestDTO;
import com.aryan.studentmanagementapi.dto.RefreshTokenRequestDTO;
import com.aryan.studentmanagementapi.dto.RegisterRequestDTO;
import com.aryan.studentmanagementapi.service.AuthService;
@RestController
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO registerRequest) {
        
        return authService.registerUser(registerRequest);
    }

    @PostMapping("/auth/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/auth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestBody RefreshTokenRequestDTO refreshTokenRequest) {

        authService.logoutUser(refreshTokenRequest);
    }

    @PostMapping("/auth/refresh")
    public AuthResponseDTO refreshTokens(@RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        System.out.println(refreshTokenRequest.getRefreshToken());
        return authService.refreshTokens(refreshTokenRequest);
    }
}
