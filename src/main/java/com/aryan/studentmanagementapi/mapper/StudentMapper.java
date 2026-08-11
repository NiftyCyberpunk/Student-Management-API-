package com.aryan.studentmanagementapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.aryan.studentmanagementapi.dto.StudentPageResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateDTO;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.model.Student;

@Component
public class StudentMapper {
    
    public Student toStudent(StudentRequestDTO dto, Branch branch){
        Student student =  new Student(
            dto.getName(),
            dto.getEmail(),
            branch,
            dto.getYear()
        );

        return student;
    }

    public StudentResponseDTO toStudentResponseDTO(Student student){

        StudentResponseDTO dto = new StudentResponseDTO(
            student.getRegistrationNo(),
            student.getName(),
            student.getBranch().getName(),
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
                    student.getBranch().getName(),
                    student.getYear()
                )
            );
        }

        return dtos;
    }

    public void updateStudent(Student student, StudentUpdateDTO dto, Branch branch){
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setBranch(branch);
        student.setYear(dto.getYear());
    }

    public StudentPageResponseDTO toStudentPageResponseDTO(Page<StudentResponseDTO> dto){
        StudentPageResponseDTO pageDto = new StudentPageResponseDTO(
            dto.getContent(),
            dto.getNumber(),
            dto.getSize(),
            dto.getTotalPages(),
            dto.getTotalElements(),
            dto.hasNext(),
            dto.hasPrevious()
        );

        return pageDto;
    }
}
