package com.aryan.studentmanagementapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.aryan.studentmanagementapi.dto.StudentPageResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentPatchDTO;
import com.aryan.studentmanagementapi.dto.StudentRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentSummaryDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateRequestResponseDTO;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.model.StudentUpdateRequest;

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

    public void updateStudent(Student student, StudentPatchDTO dto, Branch branch){
        if(dto.getName() != null){
            student.setName(dto.getName());
        }

        if(dto.getEmail() != null){
            student.setEmail(dto.getEmail());
        }

        if(branch != null){
            student.setBranch(branch);
        }

        if(dto.getYear() != null){
            student.setYear(dto.getYear());
        }
    }

    public StudentPageResponseDTO toStudentPageResponseDTO(Page<StudentSummaryDTO> dto){
        StudentPageResponseDTO repsonseDto = new StudentPageResponseDTO(
            dto.getContent(),
            dto.getNumber(),
            dto.getSize(),
            dto.getTotalPages(),
            dto.getTotalElements(),
            dto.hasNext(),
            dto.hasPrevious()
        );
        return repsonseDto;
    }

    public StudentUpdateRequestResponseDTO toStudentUpdateRequestResponse(StudentUpdateRequest request) {

        StudentUpdateRequestResponseDTO dto = new StudentUpdateRequestResponseDTO();

        dto.setId(request.getId());
        dto.setRegistrationNo(request.getStudent().getRegistrationNo());
        dto.setRequestedAt(request.getRequestedAt());
        if(request.getRequestedBranch() != null)dto.setRequestedBranch(request.getRequestedBranch().getName());
        dto.setRequestedBy(request.getRequestedBy().getUsername());
        dto.setRequestedEmail(request.getRequestedEmail());
        dto.setRequestedName(request.getRequestedName());
        dto.setRequestedYear(request.getRequestedYear());
        dto.setStatus(request.getStatus());

        return dto;
    }
}
