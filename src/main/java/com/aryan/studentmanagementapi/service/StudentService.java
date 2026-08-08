package com.aryan.studentmanagementapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.dto.StudentUpdateDTO;
import com.aryan.studentmanagementapi.exception.StudentAlreadyExistsException;
import com.aryan.studentmanagementapi.exception.StudentNotFoundException;
import com.aryan.studentmanagementapi.mapper.StudentMapper;
import com.aryan.studentmanagementapi.model.Student;
import com.aryan.studentmanagementapi.repository.StudentRepository;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final StudentMapper mapper;

    public StudentService(StudentRepository studentRepository, StudentMapper mapper){
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }
    
    public List<Student> getAllStudents(){
        return studentRepository.getAllStudents();
    }

    public Student getStudent(int registrationNo){
        return  studentRepository
            .findByRegistrationNo(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));
    }

    public Student addStudent(Student student){

        if(studentRepository.existsByRegistrationNo(student.getRegistrationNo())) {
            throw new StudentNotFoundException(student.getRegistrationNo());
        }
        if(studentRepository.existsByEmail(student.getEmail())){
            throw new StudentAlreadyExistsException(
                "Student with email " + student.getEmail() + " already exists."
            );
        }
        return studentRepository.addStudent(student);
    }

    public Student updateStudentDetails(int registrationNo, StudentUpdateDTO dto){

        Student student = studentRepository
            .findByRegistrationNo(registrationNo)
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
            .findByRegistrationNo(registrationNo)
            .orElseThrow(() -> new StudentNotFoundException(registrationNo));

        studentRepository.deleteStudent(student);
    }
}
