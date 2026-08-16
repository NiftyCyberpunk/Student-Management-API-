package com.aryan.studentmanagementapi.service;

import com.aryan.studentmanagementapi.dto.AuthResponseDTO;
import com.aryan.studentmanagementapi.dto.RegisterRequestDTO;
import com.aryan.studentmanagementapi.exception.AdminSelfDeleteException;
import com.aryan.studentmanagementapi.exception.InvalidRoleException;
import com.aryan.studentmanagementapi.exception.LastAdminException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.exception.UserNotFoundException;
import com.aryan.studentmanagementapi.exception.UsernameAlreadyExistsException;
import com.aryan.studentmanagementapi.model.RefreshToken;
import com.aryan.studentmanagementapi.model.Role;
import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
    
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private AuthResponseDTO createAuthResponseDTO(String accessToken, String refreshToken){
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AdminService(UserRepository userRepository, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponseDTO registerAdmin(RegisterRequestDTO registerRequest) {

        userRepository
            .findByUsername(registerRequest.getUsername())
            .ifPresent(existingUser -> {
                throw new UsernameAlreadyExistsException();
            }
        );

        User user = new User(
            registerRequest.getUsername(),
            passwordEncoder.encode(registerRequest.getPassword()),
            Role.ADMIN
        );

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return createAuthResponseDTO(accessToken, refreshToken.getRefreshToken());
    }

    @Transactional
    public AuthResponseDTO registerTeacher(RegisterRequestDTO registerRequest) {

        userRepository
            .findByUsername(registerRequest.getUsername())
            .ifPresent(existingUser -> {
                throw new UsernameAlreadyExistsException();
            }
        );

        User user = new User(
            registerRequest.getUsername(),
            passwordEncoder.encode(registerRequest.getPassword()),
            Role.TEACHER
        );

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return createAuthResponseDTO(accessToken, refreshToken.getRefreshToken());
    }

    public void changeRole(String username, String role){

        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> 
                new UserNotFoundException()
        );

        Role newRole;
        try {
            newRole = Role.valueOf(role.toUpperCase());
        }
        catch(IllegalArgumentException ex){
            throw new InvalidRoleException();
        }

        user.setRole(newRole);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String username, String currentUsername){

        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> 
                new UserNotFoundException()
        );

        if(user.getRole() == Role.ADMIN){

            if(username.equals(currentUsername)){
                throw new AdminSelfDeleteException();
            }

            long adminCount = userRepository.countByRole(Role.ADMIN);

            if(adminCount == 1) {
                throw new LastAdminException();
            }
        }

        refreshTokenService.deleteRefreshTokenByUser(user);
        userRepository.delete(user);
    }
}
