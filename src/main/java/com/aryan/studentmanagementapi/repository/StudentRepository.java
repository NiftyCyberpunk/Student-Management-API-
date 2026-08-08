package com.aryan.studentmanagementapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.aryan.studentmanagementapi.model.Student;

@Repository
public class StudentRepository {
    
    private List<Student> students = new ArrayList<>();

    public StudentRepository(){
        students.add(new Student(254034035, "Aryan Verma", "aryanverma@gmail.com", "CSE", 2));
        students.add(new Student(254034034, "Foad Khursheed", "zyan@gmail.com", "CSE", 2));
        students.add(new Student(254034037, "Govind Kumar", "govind@gmail.com", "CSE", 2));
        students.add(new Student(254034032, "Himesh Raj", "himesh@gmail.com", "CSE", 2));
    }

    public List<Student> getAllStudents(){
        return new ArrayList<>(students);
    }

    public Optional<Student> findByRegistrationNo(int registrationNo){
        for(Student student : students){
            if(student.getRegistrationNo() == registrationNo){
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public boolean existsByRegistrationNo(int registrationNo){
        return findByRegistrationNo(registrationNo).isPresent();
    }

    public Optional<Student> findByEmail(String email){
        for(Student student : students){
            if(student.getEmail().equals(email)){
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public boolean existsByEmail(String email){
        return findByEmail(email).isPresent();
    }

    public Student addStudent(Student student){
        students.add(student);
        return student;
    }

    public void deleteStudent(Student student){
        students.remove(student);
    }
}
