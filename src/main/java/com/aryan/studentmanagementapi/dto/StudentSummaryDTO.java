package com.aryan.studentmanagementapi.dto;

public class StudentSummaryDTO {
    private String name;
    private String branch;
    private Integer year;

    public StudentSummaryDTO(String name, String branch, Integer year){
        this.name = name;
        this.branch = branch;
        this.year = year;
    }

    public String getBranch() {
        return branch;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }
}
