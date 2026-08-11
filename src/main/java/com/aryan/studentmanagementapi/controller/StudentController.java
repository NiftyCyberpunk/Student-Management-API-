package com.aryan.studentmanagementapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.studentmanagementapi.dto.StudentResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateDTO;
import com.aryan.studentmanagementapi.dto.StudentPageResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentRequestDTO;
import com.aryan.studentmanagementapi.mapper.StudentMapper;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.response.ApiResponse;
import com.aryan.studentmanagementapi.service.StudentService;

import jakarta.validation.Valid;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper mapper;

    public StudentController(StudentService studentService, StudentMapper mapper){
        this.studentService = studentService;
        this.mapper = mapper;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot!";
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<StudentPageResponseDTO>> getStudents(Pageable pageable, @RequestParam(required = false) Integer year, @RequestParam(required = false) String branch, @RequestParam(required = false) String name){
        Page<Student> studentsPage =  studentService.getAllStudents(pageable, year, branch, name);

        Page<StudentResponseDTO> dtos = studentsPage.map(student -> {
            return mapper.toStudentResponseDTO(student);
        });

        StudentPageResponseDTO responseDTO = mapper.toStudentPageResponseDTO(dtos);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Students fetched successfully", responseDTO)
            );
    }

    @GetMapping("/students/{registrationNo}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudent(@PathVariable int registrationNo){
        Student student =  studentService.getStudent(registrationNo);

        StudentResponseDTO dto = mapper.toStudentResponseDTO(student);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Student fetched successfully", dto)
            );
    }

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> addStudent(@Valid @RequestBody StudentRequestDTO dto){

        Student savedStudent = studentService.addStudent(dto);

        StudentResponseDTO responseDto = mapper.toStudentResponseDTO(savedStudent);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Student added successfully", responseDto)
            );
    } 

    @PutMapping("/students/{registrationNo}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(@PathVariable int registrationNo, @Valid @RequestBody StudentUpdateDTO dto){

        Student updatedStudent = studentService.updateStudentDetails(registrationNo, dto);
        
        StudentResponseDTO responseDto = mapper.toStudentResponseDTO(updatedStudent);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Student updated successfully", responseDto)
            );
    }

    @DeleteMapping("/students/{registrationNo}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable int registrationNo){
        studentService.deleteStudent(registrationNo);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                new ApiResponse<>(HttpStatus.OK.value(), "Student deleted successfully.", null)
            );
    }
}