package com.aryan.studentmanagementapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.aryan.studentmanagementapi.service.CustomUserDetailsService;
import com.aryan.studentmanagementapi.service.JwtService;
import com.aryan.studentmanagementapi.service.StudentService;
import com.aryan.studentmanagementapi.service.StudentUpdateRequestService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private StudentUpdateRequestService studentUpdateRequestService;

    @Test
    void hello_shouldReturnHelloMsg() throws Exception {
       
        mockMvc
            .perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello Spring Boot!"));
    }
}
