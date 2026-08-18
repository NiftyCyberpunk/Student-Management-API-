package com.aryan.studentmanagementapi.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.studentmanagementapi.dto.AuthResponseDTO;
import com.aryan.studentmanagementapi.dto.ChangeRoleRequestDTO;
import com.aryan.studentmanagementapi.dto.RegisterRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentRequestStatusUpdateDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateRequestResponseDTO;
import com.aryan.studentmanagementapi.service.AdminService;
import com.aryan.studentmanagementapi.service.StudentUpdateRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final StudentUpdateRequestService studentUpdateRequestService;

    public AdminController(AdminService adminService, StudentUpdateRequestService studentUpdateRequestService){
        this.adminService = adminService;
        this.studentUpdateRequestService = studentUpdateRequestService;
    }

    @PostMapping("/users/admin")
    public AuthResponseDTO registerAdmin(@RequestBody RegisterRequestDTO registerRequest) {
        
        return adminService.registerAdmin(registerRequest);
    }

    @PostMapping("/users/teacher")
    public AuthResponseDTO registerTeacher(@RequestBody RegisterRequestDTO registerRequest) {
        
        return adminService.registerTeacher(registerRequest);
    }
    
    @PatchMapping("/users/{username}/role")
    public void changeRole(@PathVariable String username, @RequestBody ChangeRoleRequestDTO changeRoleRequest) {
        
        adminService.changeRole(username, changeRoleRequest.getRole());
    }

    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable String username, Authentication authentication) {

        adminService.deleteUser(username, authentication.getName());
    }

    @GetMapping("/update-requests")
    public List<StudentUpdateRequestResponseDTO> getPendingRequests() {

        return studentUpdateRequestService.getPendingRequests();
    }

    @PatchMapping("/update-requests/{requestId}")
    public void requestCheck(@Valid @RequestBody StudentRequestStatusUpdateDTO studentRequestStatusUpdate, @PathVariable Long requestId, Authentication authentication) {

        studentUpdateRequestService.updateRequestStatus(requestId, authentication.getName(), studentRequestStatusUpdate);
    }
}
