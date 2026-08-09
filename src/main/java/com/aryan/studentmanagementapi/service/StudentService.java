package com.aryan.studentmanagementapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.dto.StudentUpdateDTO;
import com.aryan.studentmanagementapi.exception.StudentAlreadyExistsException;
import com.aryan.studentmanagementapi.exception.StudentNotFoundException;
import com.aryan.studentmanagementapi.mapper.StudentMapper;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final StudentMapper mapper;

    public StudentService(StudentRepository studentRepository, StudentMapper mapper){
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }
    
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudent(int registrationNo){
        return  studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));
    }

    public Student addStudent(Student student){
        studentRepository
            .findByEmail(student.getEmail())
            .ifPresent(existingStudent -> {
                    throw new StudentAlreadyExistsException(
                        "Student with email " + student.getEmail() + " already exists."
                    );
                }
            );
    
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
        mapper.updateStudent(student, dto);

        return student;
    }

    public void deleteStudent(int registrationNo){
        Student student = studentRepository
            .findById(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));

        studentRepository.delete(student);
    }
}
