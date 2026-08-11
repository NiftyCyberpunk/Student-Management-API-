package com.aryan.studentmanagementapi.dto;

import java.util.List;

public class StudentPageResponseDTO {
    private List<StudentResponseDTO> students;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalStudents;
    private boolean hasNext;
    private boolean hasPrevious;

    public StudentPageResponseDTO(){

    }

    public StudentPageResponseDTO(List<StudentResponseDTO> students, int currentPage, int pageSize, int totalPages, long totalStudents, boolean hasNext, boolean hasPrevious){
        this.students = students;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalStudents = totalStudents;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public List<StudentResponseDTO> getStudents() {
        return students;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public boolean getHasNext() {
        return hasNext;
    }

    public boolean getHasPrevious() {
        return hasPrevious;
    }
}
