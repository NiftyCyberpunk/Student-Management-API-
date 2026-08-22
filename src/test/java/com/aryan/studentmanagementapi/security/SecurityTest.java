package com.aryan.studentmanagementapi.security;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.aryan.studentmanagementapi.config.SecurityConfig;
import com.aryan.studentmanagementapi.controller.BranchController;
import com.aryan.studentmanagementapi.service.BranchService;
import com.aryan.studentmanagementapi.mapper.BranchMapper;
import com.aryan.studentmanagementapi.service.CustomUserDetailsService;
import com.aryan.studentmanagementapi.service.JwtService;

@EnableWebSecurity
@WebMvcTest(BranchController.class)
@Import(SecurityConfig.class)
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BranchService branchService;

    @MockitoBean
    private BranchMapper mapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void getBranches_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        mockMvc
            .perform(get("/branches"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void getBranches_shouldAcceptRequest_whenAdminRequests() throws Exception {
        String token = "admin-token";

        when(jwtService.extractUsername(token))
            .thenReturn("admin");
        
        UserDetails admin = User.withUsername("admin")
            .password("password")
            .roles("ADMIN")
            .build();
        
        when(customUserDetailsService.loadUserByUsername("admin"))
            .thenReturn(admin);
        
        mockMvc
            .perform(get("/branches").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }

    @Test
    void getBranches_shouldReturnForbidden_whenTeacherRequests() throws Exception {
        String token = "teacher-token";

        when(jwtService.extractUsername(token))
            .thenReturn("teacher");
        
        UserDetails teacher = User.withUsername("teacher")
            .password("password")
            .roles("TEACHER")
            .build();
        
        when(customUserDetailsService.loadUserByUsername("teacher"))
            .thenReturn(teacher);
        
        mockMvc
            .perform(get("/branches").header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    void getBranches_shouldReturnUnauthorize_whenDisabledUserRequests() throws Exception {

        String token = "disbaled-user-token";

        when(jwtService.extractUsername(token))
            .thenReturn("disabled-user");

        UserDetails disabledUser = User.withUsername("disabled-user")
            .password("password")
            .roles("TEACHER")
            .disabled(true)
            .build();
        
        when(customUserDetailsService.loadUserByUsername("disabled-user"))
            .thenReturn(disabledUser);

        mockMvc
            .perform(get("/branches").header("Authorization", "Bearer " + token))
            .andExpect(status().isUnauthorized());
    }
}