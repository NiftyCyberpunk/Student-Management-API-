package com.aryan.studentmanagementapi.exception;

public class BranchNotFoundException extends RuntimeException{
    
    public BranchNotFoundException(String name){
        super(
            "Branch with the name " + name + " is not found."
        );
    }
}
