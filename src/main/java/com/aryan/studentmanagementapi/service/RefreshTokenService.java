package com.aryan.studentmanagementapi.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aryan.studentmanagementapi.exception.RefreshTokenExpiredException;
import com.aryan.studentmanagementapi.exception.RefreshTokenRevokedException;
import com.aryan.studentmanagementapi.model.RefreshToken;
import com.aryan.studentmanagementapi.model.User;
import com.aryan.studentmanagementapi.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    @Value("${refresh_token_expiry}")
    private long refreshTokenExpiry;
    
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
    } 

    public RefreshToken createRefreshToken(User user){

        RefreshToken refreshToken = new RefreshToken();

        SecureRandom secureRandom = new SecureRandom();

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        refreshToken.setRefreshToken(token);

        refreshToken.setExpiry(LocalDateTime.now().plusDays(refreshTokenExpiry));

        refreshToken.setUser(user);

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken findRefreshToken(String token) {
        
        RefreshToken refreshToken = refreshTokenRepository
            .findByRefreshToken(token)
            .orElseThrow(() ->
                new RefreshTokenRevokedException()
            );
        
        return refreshToken;
    }

    public boolean isExpired(RefreshToken refreshToken){

        return !refreshToken.getExpiry().isAfter(LocalDateTime.now());
    }

    public RefreshToken validateRefreshToken(RefreshToken refreshToken){

        if(isExpired(refreshToken)){
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException();
        }

        return refreshToken;
    }

    public void deleteRefreshToken(RefreshToken refreshToken){

        refreshTokenRepository.delete(refreshToken);
    }
}
