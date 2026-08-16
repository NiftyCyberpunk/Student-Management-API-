package com.aryan.studentmanagementapi.exception;

public class LastAdminException extends RuntimeException {

    public LastAdminException(){
        super("Cannot remove the last admin.");
    }
}
