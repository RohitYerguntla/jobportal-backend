package com.jobportal.backend.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET =
            "jobportalprojectsecretkeyjobportalprojectsecretkey";

    private final Key key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes());

    // Generate Token
    public String generateToken(
            String email,
            String role) {

        Map<String, Object> claims =
                new HashMap<>();

        claims.put("role", role);

        return Jwts.builder()

                .claims(claims)

                .subject(email)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60))

                .signWith(
                        key,
                        SignatureAlgorithm.HS256)

                .compact();
    }

    // Extract Email
    public String extractEmail(
            String token) {

        return getClaims(token)
                .getSubject();
    }

    // Extract Role
    public String extractRole(
            String token) {

        return getClaims(token)
                .get("role", String.class);
    }

    // Claims
    private Claims getClaims(
            String token) {

        return Jwts.parser()

                .verifyWith(
                        (SecretKey) key)

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }

    // Validate
    public boolean validateToken(
            String token,
            String email,
            String role) {

        String extractedEmail =
                extractEmail(token);

        String extractedRole =
                extractRole(token);

        return extractedEmail.equals(email)
                && extractedRole.equals(role);
    }
}