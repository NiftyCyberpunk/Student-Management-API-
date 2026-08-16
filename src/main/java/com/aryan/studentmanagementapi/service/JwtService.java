package com.aryan.studentmanagementapi.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        byte[] KeyBytes = Decoders.BASE64.decode(secret);

        this.secretKey = Keys.hmacShaKeyFor(KeyBytes);
    }

    public String generateToken(String username) {

        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
            .signWith(secretKey)
            .compact();
    }

    public boolean isTokenValid(String token, String username) {

        String tokenUsername = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
        
        return tokenUsername.equals(username);
    }

    public String extractUsername(String token) {

        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
}
