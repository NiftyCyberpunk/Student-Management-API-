package com.aryan.studentmanagementapi.service;

import com.aryan.studentmanagementapi.exception.AdminSelfDeleteException;
import com.aryan.studentmanagementapi.exception.InvalidRoleException;
import com.aryan.studentmanagementapi.exception.LastAdminException;

import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.exception.UserNotFoundException;
import com.aryan.studentmanagementapi.model.Role;
import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
    
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public AdminService(UserRepository userRepository, RefreshTokenService refreshTokenService){
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
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
