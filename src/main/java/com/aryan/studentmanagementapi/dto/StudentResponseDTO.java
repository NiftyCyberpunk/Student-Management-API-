package com.aryan.studentmanagementapi.dto;

public class StudentResponseDTO {
    private Integer registrationNo;   
    private String name; 
    private String branch;
    private Integer year;

    public StudentResponseDTO(Integer registrationNo, String name, String branch, Integer year){
        this.registrationNo = registrationNo;
        this.name = name;
        this.branch = branch;
        this.year = year;
    }

    public Integer getRegistrationNo(){
        return this.registrationNo;
    }

    public String getName(){
        return this.name;
    }

    public String getBranch(){
        return this.branch;
    }

    public Integer getYear(){
        return this.year;
    }
}
