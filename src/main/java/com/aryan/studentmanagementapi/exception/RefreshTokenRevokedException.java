package com.aryan.studentmanagementapi.exception;

public class RefreshTokenRevokedException extends RuntimeException {
    
    public RefreshTokenRevokedException(){
        super("Refresh token revoked.");
    }
}
