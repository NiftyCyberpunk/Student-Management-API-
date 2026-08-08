package com.aryan.studentmanagementapi.exception;

public class StudentAlreadyExistsException extends RuntimeException {
    
    public StudentAlreadyExistsException(String message){
        super(message);
    }
}
