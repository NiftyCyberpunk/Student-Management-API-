package com.aryan.studentmanagementapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.studentmanagementapi.dto.StudentResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentPageResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentPatchDTO;
import com.aryan.studentmanagementapi.dto.StudentRequestDTO;
import com.aryan.studentmanagementapi.response.ApiResponse;
import com.aryan.studentmanagementapi.service.StudentService;
import com.aryan.studentmanagementapi.service.StudentUpdateRequestService;

import jakarta.validation.Valid;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final StudentUpdateRequestService studentUpdateRequestService;

    public StudentController(StudentService studentService, StudentUpdateRequestService studentUpdateRequestService){
        this.studentService = studentService;
        this.studentUpdateRequestService = studentUpdateRequestService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot!";
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<StudentPageResponseDTO>> getStudents(Pageable pageable, @RequestParam(required = false) Integer year, @RequestParam(required = false) String branch, @RequestParam(required = false) String name){

        StudentPageResponseDTO dto = studentService.getAllStudents(pageable, year, branch, name);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Students fetched successfully", dto)
            );
    }

    @GetMapping("/students/{registrationNo}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudent(@PathVariable Integer registrationNo, Authentication authentication){

        StudentResponseDTO dto = studentService.getStudent(registrationNo, authentication.getName());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Student fetched successfully", dto)
            );
    }

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> addStudent(@Valid @RequestBody StudentRequestDTO dto){

        StudentResponseDTO responseDto = studentService.addStudent(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Student added successfully", responseDto)
            );
    } 

    @PatchMapping("/students/{registrationNo}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(@PathVariable Integer registrationNo, @Valid @RequestBody StudentPatchDTO dto){
        
        StudentResponseDTO responseDto = studentService.updateStudentDetails(registrationNo, dto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Student updated successfully", responseDto)
            );
    }

    @DeleteMapping("/students/{registrationNo}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Integer registrationNo){
        studentService.deleteStudent(registrationNo);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Student deleted successfully.", null)
            );
    }

    @PostMapping("/students/{registrationNo}/update-request")
    public void requestUpdate(@PathVariable Integer registrationNo, @Valid @RequestBody StudentUpdateRequestDTO studentUpdateRequest, Authentication authentication) {
        
        studentUpdateRequestService.createRequest(registrationNo, authentication.getName(), studentUpdateRequest);
    }
}