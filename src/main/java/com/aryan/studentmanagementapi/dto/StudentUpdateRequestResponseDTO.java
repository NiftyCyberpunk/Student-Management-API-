package com.aryan.studentmanagementapi.dto;

import java.time.LocalDateTime;

import com.aryan.studentmanagementapi.model.UpdateRequestStatus;

public class StudentUpdateRequestResponseDTO {

    private Long id;
    private Integer registrationNo;
    private String requestedBy;
    private String requestedName;
    private String requestedEmail;
    private String requestedBranch;
    private Integer requestedYear;
    private UpdateRequestStatus status;
    private LocalDateTime requestedAt;

    public Long getId() {
        return id;
    }
    public Integer getRegistrationNo() {
        return registrationNo;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public String getRequestedBranch() {
        return requestedBranch;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public String getRequestedEmail() {
        return requestedEmail;
    }

    public String getRequestedName() {
        return requestedName;
    }
    
    public Integer getRequestedYear() {
        return requestedYear;
    }
    
    public UpdateRequestStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setRegistrationNo(Integer registrationNo) {
        this.registrationNo = registrationNo;
    }
    
    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public void setRequestedBranch(String requestedBranch) {
        this.requestedBranch = requestedBranch;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public void setRequestedEmail(String requestedEmail) {
        this.requestedEmail = requestedEmail;
    }

    public void setRequestedName(String requestedName) {
        this.requestedName = requestedName;
    }
    
    public void setRequestedYear(Integer requestedYear) {
        this.requestedYear = requestedYear;
    }

    public void setStatus(UpdateRequestStatus status) {
        this.status = status;
    }
}
