package com.aryan.studentmanagementapi.dto;

import com.aryan.studentmanagementapi.validation.NullableNotBlank;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class StudentUpdateRequestDTO {
    
    @NullableNotBlank
    private String name;

    @Email
    @NullableNotBlank
    private String email;

    @NullableNotBlank
    private String branch;

    @Min(1)
    @Max(4)
    private Integer year;

    public String getBranch() {
        return branch;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
