package com.aryan.studentmanagementapi.exception;

public class StudentNotFoundException extends RuntimeException {
    
    public StudentNotFoundException(String message){
        super(message);
    }
}
