package com.aryan.studentmanagementapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.dto.RegisterRequestDTO;
import com.aryan.studentmanagementapi.exception.UsernameAlreadyExistsException;
import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegisterRequestDTO registerRequest) {

        userRepository
            .findByUsername(registerRequest.getUsername())
            .ifPresent(existingUser -> {
                throw new UsernameAlreadyExistsException();
            }
        );

        User user = new User(
            registerRequest.getUsername(),
            passwordEncoder.encode(registerRequest.getPassword()),
            "STUDENT"
        );

        userRepository.save(user);

        String token = jwtService.generateToken(registerRequest.getUsername());

        return token;
    }
}
