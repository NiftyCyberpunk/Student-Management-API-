package com.aryan.studentmanagementapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StudentUpdateDTO {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String branch;
    @Min(1)
    @Max(4)
    private int year;

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getBranch(){
        return this.branch;
    }

    public int getYear(){
        return this.year;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setBranch(String branch){
        this.branch = branch;
    }

    public void setYear(int year){
        this.year = year;
    }
}
