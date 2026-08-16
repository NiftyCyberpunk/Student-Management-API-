package com.aryan.studentmanagementapi.exception;


public class AdminSelfDeleteException extends RuntimeException {
    
    public AdminSelfDeleteException(){
        super("Admin cannot delete their own account.");
    }
}
