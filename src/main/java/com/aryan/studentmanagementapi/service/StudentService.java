package com.aryan.studentmanagementapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.dto.StudentRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentUpdateDTO;
import com.aryan.studentmanagementapi.exception.BranchNotFoundException;
import com.aryan.studentmanagementapi.exception.StudentAlreadyExistsException;
import com.aryan.studentmanagementapi.exception.StudentNotFoundException;
import com.aryan.studentmanagementapi.mapper.StudentMapper;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.repository.BranchRepository;
import com.aryan.studentmanagementapi.repository.StudentRepository;
import com.aryan.studentmanagementapi.specification.StudentSpecification;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final StudentMapper mapper;

    public StudentService(StudentRepository studentRepository, StudentMapper mapper, BranchRepository branchRepository){
        this.studentRepository = studentRepository;
        this.branchRepository = branchRepository;
        this.mapper = mapper;
    }
    
    public Page<Student> getAllStudents(Pageable pageable, Integer year, String branch, String name){

        Specification<Student> spec = StudentSpecification.alwaysTrue();

        if(year != null){
            spec = spec.and(StudentSpecification.hasYear(year));
        }

        if(branch != null){
            spec = spec.and(StudentSpecification.hasBranch(branch));
        }

        if(name != null){
            spec = spec.and(StudentSpecification.hasNameLike(name));
        }

        return studentRepository.findAll(spec, pageable);
    }

    public Student getStudent(int registrationNo){
        return  studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));
    }

    public Student addStudent(StudentRequestDTO dto){
        studentRepository
            .findByEmail(dto.getEmail())
            .ifPresent(existingStudent -> {
                    throw new StudentAlreadyExistsException(
                        "Student with email " + dto.getEmail() + " already exists."
                    );
                }
            );
        Branch branch = branchRepository
            .findByName(dto.getBranch())
            .orElseThrow(() -> new BranchNotFoundException(
                    "The Branch name " + dto.getBranch() + " not exists."
                )
            );
        
        Student student = mapper.toStudent(dto, branch);
    
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudentDetails(int registrationNo, StudentUpdateDTO dto){

        Student student = studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));

        studentRepository
            .findByEmail(dto.getEmail())
            .ifPresent(foundStudent -> {
                    if(foundStudent.getRegistrationNo() != student.getRegistrationNo()){
                        throw new StudentAlreadyExistsException(
                            "Student with email " + dto.getEmail() + " already exists."
                        );
                    }
                } 
            );

        Branch branch = branchRepository
            .findByName(dto.getBranch())
            .orElseThrow(() -> new BranchNotFoundException(
                    "The Branch name " + dto.getBranch() + " not exists."
                )
            );
        
        mapper.updateStudent(student, dto, branch);

        return student;
    }

    public void deleteStudent(int registrationNo){
        Student student = studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));

        studentRepository.delete(student);
    }
}
