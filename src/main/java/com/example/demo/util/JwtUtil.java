package com.example.demo.util;

import com.example.demo.entity.RoleEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(String email, Set<RoleEntity> roles) {
        List<String> roleList = roles.stream()
                .map(RoleEntity::getRole)
                .toList();

        return Jwts.builder()
                .subject(email)
                .claim("roles", roleList)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims getPayload(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        Claims claims = getPayload(token);
        return !isTokenExpired(claims);
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract token
        }

        return null;
    }
}
