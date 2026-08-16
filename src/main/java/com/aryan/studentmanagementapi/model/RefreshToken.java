package com.aryan.studentmanagementapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiry;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public RefreshToken(){
        
    }

    public Long getId() {
        return Id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public User getUser() {
        return user;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
