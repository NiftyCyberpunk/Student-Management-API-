package com.aryan.studentmanagementapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer registrationNo; 

    @Column(nullable = false)  
    private String name;

    @Column(nullable = false,unique = true) 
    private String email;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Min(1)
    @Max(4)
    private int year;

    protected Student(){

    }

    public Student(String name, String email, Branch branch, int year){
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.year = year;
    }
    
    public Integer getRegistrationNo(){
        return this.registrationNo;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public Branch getBranch(){
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

    public void setBranch(Branch branch){
        this.branch = branch;
    }

    public void setYear(int year){
        this.year = year;
    }
}
