package com.aryan.studentmanagementapi.exception;

public class RefreshTokenExpiredException extends RuntimeException {
    
    public RefreshTokenExpiredException() {
        super("Refresh Token expired.");
    }
}
