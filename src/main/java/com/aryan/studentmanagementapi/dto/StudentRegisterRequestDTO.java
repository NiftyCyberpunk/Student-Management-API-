package com.aryan.studentmanagementapi.dto;

public class StudentRegisterRequestDTO {
    
    private Integer registrationNumber;
    private String username;
    private String password;

    public Integer getRegistrationNumber() {
        return registrationNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setRegistrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}