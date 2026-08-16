package com.aryan.studentmanagementapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.dto.StudentPageResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentPatchDTO;
import com.aryan.studentmanagementapi.dto.StudentRequestDTO;
import com.aryan.studentmanagementapi.dto.StudentResponseDTO;
import com.aryan.studentmanagementapi.dto.StudentSummaryDTO;
import com.aryan.studentmanagementapi.exception.BranchNotFoundException;
import com.aryan.studentmanagementapi.exception.StudentAlreadyExistsException;
import com.aryan.studentmanagementapi.exception.StudentNotFoundException;
import com.aryan.studentmanagementapi.exception.UserNotFoundException;
import com.aryan.studentmanagementapi.mapper.StudentMapper;
import com.aryan.studentmanagementapi.model.Branch;
import com.aryan.studentmanagementapi.model.Role;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.BranchRepository;
import com.aryan.studentmanagementapi.repository.StudentRepository;
import com.aryan.studentmanagementapi.repository.UserRepository;
import com.aryan.studentmanagementapi.specification.StudentSpecification;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final StudentMapper mapper;

    public StudentService(StudentRepository studentRepository, StudentMapper mapper, BranchRepository branchRepository, UserRepository userRepository){
        this.studentRepository = studentRepository;
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
    
    public StudentPageResponseDTO getAllStudents(Pageable pageable, Integer year, String branch, String name){

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

        Page<StudentSummaryDTO> studentsPageDto =  studentRepository.findAllProjected(spec, pageable);

        return mapper.toStudentPageResponseDTO(studentsPageDto);
    }

    public StudentResponseDTO getStudent(int registrationNo, String currentUsername){

        User user = userRepository
            .findByUsername(currentUsername)
            .orElseThrow(() -> new UserNotFoundException());

        if(user.getRole() == Role.STUDENT){
            if(user.getStudent().getRegistrationNo().equals(registrationNo)){
                throw new AccessDeniedException("You do not have access to this student");
            }
        }

        Student student = studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));

        return mapper.toStudentResponseDTO(student);
    }

    @Transactional
    public StudentResponseDTO addStudent(StudentRequestDTO dto){
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
            .orElseThrow(() -> new BranchNotFoundException(dto.getBranch())
            );
        
        Student student = mapper.toStudent(dto, branch);
    
        Student savedStudent =  studentRepository.save(student);

        return mapper.toStudentResponseDTO(savedStudent);
    }

    @Transactional
    public StudentResponseDTO updateStudentDetails(int registrationNo, StudentPatchDTO dto){

        Student student = studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));

        if(dto.getEmail() != null){
            studentRepository
                .findByEmail(dto.getEmail())
                .ifPresent(foundStudent -> {
                        if(!foundStudent.getRegistrationNo().equals(student.getRegistrationNo())){
                            throw new StudentAlreadyExistsException(
                                "Student with email " + dto.getEmail() + " already exists."
                            );
                        }
                    } 
                );
        }

        Branch branch = null;

        if(dto.getBranch() != null){
            branch = branchRepository
                .findByName(dto.getBranch())
                .orElseThrow(() -> new BranchNotFoundException(dto.getBranch())
                );
        }
        
        mapper.updateStudent(student, dto, branch);

        return mapper.toStudentResponseDTO(student);
    }

    @Transactional
    public void deleteStudent(int registrationNo){
        Student student = studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));

        studentRepository.delete(student);
    }
}
