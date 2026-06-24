// Path: src/main/java/com/campus/marketplace/security/JwtUtil.java

package com.campus.marketplace.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key loaded from application.properties / environment variable
    @Value("${jwt.secret}")
    private String SECRET;

    private static final long EXPIRY = 1000 * 60 * 60 * 24; // 24 hours in ms

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Step 1: Generate token from email
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)                         // who this token belongs to
                .setIssuedAt(new Date())                   // when it was created
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY)) // expires in 24h
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // sign it
                .compact();
    }

    // Step 2: Extract email FROM token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Step 3: Validate token
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // expired, tampered, or invalid
        }
    }
}