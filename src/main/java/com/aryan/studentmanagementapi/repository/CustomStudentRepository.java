package com.aryan.studentmanagementapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.aryan.studentmanagementapi.dto.StudentSummaryDTO;
import com.aryan.studentmanagementapi.model.Student;

public interface CustomStudentRepository {
    
    Page<StudentSummaryDTO> findAllProjected(Specification<Student> spec, Pageable pageable);
}
