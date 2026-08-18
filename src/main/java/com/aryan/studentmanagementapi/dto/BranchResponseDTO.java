package com.aryan.studentmanagementapi.dto;

public class BranchResponseDTO {
    private Integer id;
    private String name;

    public BranchResponseDTO(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
