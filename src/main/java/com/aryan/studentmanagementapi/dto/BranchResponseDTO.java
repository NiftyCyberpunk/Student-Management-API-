package com.aryan.studentmanagementapi.dto;

public class BranchResponseDTO {
    private int id;
    private String name;

    public BranchResponseDTO(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
