package com.aryan.studentmanagementapi.exception;

public class InvalidRoleException extends RuntimeException {
    
    public InvalidRoleException(){
        super("Invalid role. Allowed roles are STUDENT, TEACHER, and ADMIN.");
    }
}
