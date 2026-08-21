package com.aryan.studentmanagementapi.service;

import com.aryan.studentmanagementapi.dto.AuthResponseDTO;
import com.aryan.studentmanagementapi.dto.RegisterRequestDTO;
import com.aryan.studentmanagementapi.exception.AdminSelfDeleteException;
import com.aryan.studentmanagementapi.exception.BadRequestException;
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

    private AuthResponseDTO registerUser(RegisterRequestDTO registerRequest, Role role) {
        userRepository
            .findByUsername(registerRequest.getUsername())
            .ifPresent(existingUser -> {
                throw new UsernameAlreadyExistsException();
            }
        );

        User user = new User(
            registerRequest.getUsername(),
            passwordEncoder.encode(registerRequest.getPassword()),
            role
        );

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponseDTO(accessToken, refreshToken.getRefreshToken());
    }

    public AdminService(UserRepository userRepository, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponseDTO registerAdmin(RegisterRequestDTO registerRequest) {

        return registerUser(registerRequest, Role.ADMIN);
    }

    @Transactional
    public AuthResponseDTO registerTeacher(RegisterRequestDTO registerRequest) {

        return registerUser(registerRequest, Role.TEACHER);
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
    public void disableUser(String username, String currentUsername){

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

        if(!user.isEnabled()){
            throw new BadRequestException("Cannot disable the disabled user.");
        }

        refreshTokenService.deleteRefreshTokenByUser(user);
        user.setEnabled(false);
        userRepository.save(user);
    }
}
