package com.aryan.studentmanagementapi.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(int registrationNo){
        super("Student with the registration number " + registrationNo + " not found.");
    }
    
    public StudentNotFoundException(String message){
        super(message);
    }
}
