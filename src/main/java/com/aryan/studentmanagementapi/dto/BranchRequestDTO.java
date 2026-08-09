package com.aryan.studentmanagementapi.dto;

import jakarta.validation.constraints.NotBlank;

public class BranchRequestDTO {
    @NotBlank
    private String name;

    public BranchRequestDTO(){

    }

    public BranchRequestDTO(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
