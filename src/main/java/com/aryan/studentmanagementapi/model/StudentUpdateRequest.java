package com.aryan.studentmanagementapi.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class StudentUpdateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requested_by", nullable = false)
    private User requestedBy;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private String requestedName;

    @Email
    private String requestedEmail;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch requestedBranch;

    @Min(1)
    @Max(4)
    private Integer requestedYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UpdateRequestStatus status = UpdateRequestStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "processed_by")
    private User processedBy;

    private LocalDateTime processedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    public Long getId() {
        return id;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public Branch getRequestedBranch() {
        return requestedBranch;
    }

    public User getRequestedBy() {
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

    public Student getStudent() {
        return student;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public User getProcessedBy() {
        return processedBy;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public void setRequestedBranch(Branch requestedBranch) {
        this.requestedBranch = requestedBranch;
    }

    public void setRequestedBy(User requestedBy) {
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

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public void setProcessedBy(User processedBy) {
        this.processedBy = processedBy;
    }
}
