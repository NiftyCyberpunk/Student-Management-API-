package com.aryan.studentmanagementapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.studentmanagementapi.dto.ChangeRoleRequestDTO;
import com.aryan.studentmanagementapi.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }
    
    @PatchMapping("/users/{username}/role")
    public void changeRole(@PathVariable String username, @RequestBody ChangeRoleRequestDTO changeRoleRequest) {
        
        adminService.changeRole(username, changeRoleRequest.getRole());
    }

    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username, Authentication authentication){

        adminService.deleteUser(username, authentication.getName());
    }
}
