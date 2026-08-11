package com.aryan.studentmanagementapi.dto;

public class BranchStudentCountDTO {
    
    private String name;
    private long studentCount;

    public BranchStudentCountDTO(String name, long studentCount){
        this.name = name;
        this.studentCount = studentCount;
    }

    public String getName() {
        return name;
    }

    public long getStudentCount() {
        return studentCount;
    }
}
