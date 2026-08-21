package com.aryan.studentmanagementapi.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private List<String> errors;

    public ApiError(int status, String message, List<String> errors){
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public int getStatus(){
        return this.status;
    }

    public String getMessage(){
        return this.message;
    }

    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }

    public List<String> getErrors(){
        return this.errors;
    }
}
