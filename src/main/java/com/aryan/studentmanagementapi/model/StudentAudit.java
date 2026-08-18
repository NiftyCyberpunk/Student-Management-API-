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

@Entity
public class StudentAudit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime changedAt;

    public Long getId() {
        return id;
    }

    public AuditAction getAction() {
        return action;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public Student getStudent() {
        return student;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
