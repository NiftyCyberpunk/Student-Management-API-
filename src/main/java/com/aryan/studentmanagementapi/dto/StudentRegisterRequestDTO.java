package com.aryan.studentmanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentRegisterRequestDTO {
    
    @NotNull
    private Integer registrationNumber;

    @NotBlank
    private String username;

    @NotBlank
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