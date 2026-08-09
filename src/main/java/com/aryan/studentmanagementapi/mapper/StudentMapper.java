package com.aryan.studentmanagementapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.aryan.studentmanagementapi.dto.StudentRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateDTO;
import com.aryan.studentmanagementapi.model.Student;

@Component
public class StudentMapper {
    
    public Student toStudent(StudentRequestDTO dto){
        Student student =  new Student(
            dto.getName(),
            dto.getEmail(),
            dto.getBranch(),
            dto.getYear()
        );

        return student;
    }

    public StudentResponseDTO toStudentResponseDTO(Student student){

        StudentResponseDTO dto = new StudentResponseDTO(
            student.getRegistrationNo(),
            student.getName(),
            student.getBranch(),
            student.getYear()
        );

        return dto;
    }

    public List<StudentResponseDTO> toStudentResponseDTOs(List<Student> students){
        List<StudentResponseDTO> dtos = new ArrayList<>();

        for(Student student : students){
            dtos.add(
                new StudentResponseDTO(
                    student.getRegistrationNo(),
                    student.getName(),
                    student.getBranch(),
                    student.getYear()
                )
            );
        }

        return dtos;
    }

    public void updateStudent(Student student, StudentUpdateDTO dto){
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setBranch(dto.getBranch());
        student.setYear(dto.getYear());
    }
}
