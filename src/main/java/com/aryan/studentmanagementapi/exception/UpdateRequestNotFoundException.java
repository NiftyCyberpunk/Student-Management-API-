package com.aryan.studentmanagementapi.exception;

public class UpdateRequestNotFoundException extends RuntimeException {
    
    public UpdateRequestNotFoundException() {
        super("Update Request not found.");
    }
}
