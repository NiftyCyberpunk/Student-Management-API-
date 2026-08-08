package com.aryan.studentmanagementapi.service;

import java.util.List;
import java.util.Optional;

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
        Optional<Student> student =  studentRepository.findByRegistrationNo(registrationNo);

        if(student.isEmpty()){
            throw new StudentNotFoundException(
                "Student with registration number " + registrationNo + " not found."
            );
        }
        return student.get();
    }

    public Student addStudent(Student student){

        if(studentRepository.existsByRegistrationNo(student.getRegistrationNo())) {
            throw new StudentAlreadyExistsException(
                "Student with registration number " + student.getRegistrationNo() + " already exists."
            );
        }
        if(studentRepository.existsByEmail(student.getEmail())){
            throw new StudentAlreadyExistsException(
                "Student with email " + student.getEmail() + " already exists."
            );
        }
        return studentRepository.addStudent(student);
    }

    public Student updateStudentDetails(int registrationNo, StudentUpdateDTO dto){

        Optional<Student> studentObj = studentRepository.findByRegistrationNo(registrationNo);

        if(studentObj.isEmpty()){
            throw new StudentNotFoundException(
                "Student with registration number " + registrationNo + " not found."
            );
        }

        Student student = studentObj.get();

        Optional<Student> studentWithEmail = studentRepository.findByEmail(dto.getEmail());

        if(studentWithEmail.isPresent()){

            Student foundStudent = studentWithEmail.get();

            if(foundStudent.getRegistrationNo() != student.getRegistrationNo()){
                throw new StudentAlreadyExistsException(
                    "Student with email " + dto.getEmail() + " already exists."
                );
            }
        }
        /* 
            if(!student.getEmail().equals(dto.getEmail())){
                if(studentRepository.existsByEmail(dto.getEmail())){
                    throw new StudentAlreadyExistsException(
                        "Student with email " + dto.getEmail() + " already exists."
                    );
                }
            }
        */
        mapper.updateStudent(student, dto);

        return student;
    }

    public void deleteStudent(int registrationNo){
        Optional<Student> student = studentRepository.findByRegistrationNo(registrationNo);

        if(student.isEmpty()){
            throw new StudentNotFoundException(
                "Student with registration number " + registrationNo + " not found."
            );
        }

        studentRepository.deleteStudent(student.get());
    }
}
