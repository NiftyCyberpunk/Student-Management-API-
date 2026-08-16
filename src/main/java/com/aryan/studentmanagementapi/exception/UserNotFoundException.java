package com.aryan.studentmanagementapi.exception;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(){
        super("User not found.");
    }
}
