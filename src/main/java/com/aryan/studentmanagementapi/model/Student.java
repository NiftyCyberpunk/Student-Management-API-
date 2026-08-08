package com.aryan.studentmanagementapi.model;


public class Student {
    private int registrationNo;   
    private String name; 
    private String email;
    private String branch;
    private int year;

    public Student(int registrationNo, String name, String email, String branch, int year){
        this.registrationNo = registrationNo;
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
    }
    
    public int getRegistrationNo(){
        return this.registrationNo;
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

    public int getYear(){
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

    public void setYear(int year){
        this.year = year;
    }
}
