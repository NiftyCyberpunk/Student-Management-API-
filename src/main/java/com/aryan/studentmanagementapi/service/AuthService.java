package com.aryan.studentmanagementapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aryan.studentmanagementapi.dto.AuthResponseDTO;
import com.aryan.studentmanagementapi.dto.LoginRequestDTO;
import com.aryan.studentmanagementapi.dto.RefreshTokenRequestDTO;
import com.aryan.studentmanagementapi.dto.RegisterRequestDTO;
import com.aryan.studentmanagementapi.exception.UserNotFoundException;
import com.aryan.studentmanagementapi.exception.UsernameAlreadyExistsException;
import com.aryan.studentmanagementapi.model.RefreshToken;
import com.aryan.studentmanagementapi.model.Role;
import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private AuthResponseDTO createAuthResponseDTO(String accessToken, String refreshToken){
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AuthService(UserRepository userRepository, JwtService jwtService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponseDTO registerUser(RegisterRequestDTO registerRequest) {

        userRepository
            .findByUsername(registerRequest.getUsername())
            .ifPresent(existingUser -> {
                throw new UsernameAlreadyExistsException();
            }
        );

        User user = new User(
            registerRequest.getUsername(),
            passwordEncoder.encode(registerRequest.getPassword()),
            Role.STUDENT
        );

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return createAuthResponseDTO(accessToken, refreshToken.getRefreshToken());
    }

    @Transactional
    public AuthResponseDTO loginUser(LoginRequestDTO loginRequest) {

        Authentication authentication = 
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );

        String accessToken = jwtService.generateToken(authentication.getName());

        User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> 
                new UserNotFoundException()
            );

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        
        return createAuthResponseDTO(accessToken, refreshToken.getRefreshToken());
    }

    @Transactional
    public AuthResponseDTO refreshTokens(RefreshTokenRequestDTO refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenRequest.getRefreshToken());

        RefreshToken validRefreshToken = refreshTokenService.validateRefreshToken(refreshToken);

        refreshTokenService.deleteRefreshToken(validRefreshToken);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(validRefreshToken.getUser());

        String accessToken = jwtService.generateToken(validRefreshToken.getUser().getUsername());

        return createAuthResponseDTO(accessToken, newRefreshToken.getRefreshToken());
    }

    @Transactional
    public void logoutUser(RefreshTokenRequestDTO refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenRequest.getRefreshToken());

        refreshTokenService.deleteRefreshToken(refreshToken);
    }
}
