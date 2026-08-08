package com.aryan.studentmanagementapi.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private T data;

    public ApiResponse(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
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

    public T getData(){
        return this.data;
    }
}
