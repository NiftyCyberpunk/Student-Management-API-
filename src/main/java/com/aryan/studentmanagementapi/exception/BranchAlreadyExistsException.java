package com.aryan.studentmanagementapi.exception;

public class BranchAlreadyExistsException extends RuntimeException {
    public BranchAlreadyExistsException(String message){
        super(message);
    }
}
