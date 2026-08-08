package com.aryan.studentmanagementapi.dto;

public class StudentResponseDTO {
    private int registrationNo;   
    private String name; 
    private String branch;
    private int year;

    public StudentResponseDTO(int registrationNo, String name, String branch, int year){
        this.registrationNo = registrationNo;
        this.name = name;
        this.branch = branch;
        this.year = year;
    }

    public int getRegistrationNo(){
        return this.registrationNo;
    }

    public String getName(){
        return this.name;
    }

    public String getBranch(){
        return this.branch;
    }

    public int getYear(){
        return this.year;
    }
}
