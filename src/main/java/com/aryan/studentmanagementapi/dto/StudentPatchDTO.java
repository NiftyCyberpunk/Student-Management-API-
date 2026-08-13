package com.aryan.studentmanagementapi.dto;

import com.aryan.studentmanagementapi.validation.NullableNotBlank;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class StudentPatchDTO {

    @NullableNotBlank
    private String name;

    @Email
    private String email;

    @NullableNotBlank
    private String branch;
    
    @Min(1)
    @Max(4)
    private Integer year;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBranch() {
        return branch;
    }

    public Integer getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
