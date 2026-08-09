package com.aryan.studentmanagementapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer registrationNo; 
    @Column(nullable = false)  
    private String name;
    @Column(nullable = false,unique = true) 
    private String email;
    @Column(nullable = false)
    private String branch;
    private int year;

    protected Student(){

    }

    public Student(String name, String email, String branch, int year){
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
