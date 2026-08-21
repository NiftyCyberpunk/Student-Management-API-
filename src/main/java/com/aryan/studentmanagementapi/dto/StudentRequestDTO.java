package com.aryan.studentmanagementapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String branch;
    
    @NotNull
    @Min(1)
    @Max(4)
    private Integer year;

    public StudentRequestDTO(){

    }

    public StudentRequestDTO(String name, String email, String branch, Integer year){
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getBranch(){
        return this.branch;
    }

    public Integer getYear(){
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

    public void setYear(Integer year){
        this.year = year;
    }
}
