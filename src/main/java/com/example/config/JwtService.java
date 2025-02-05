package com.example.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "qwertyuiopasdfghjklzxcvbnmqwertyuioplkmsdjcvgsdnbvcxsaqwedfg";
    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 minute
    private static final long REFRESH_TOKEN_EXPIRATION = 48 * 60 * 60 * 1000; // 48 hours

    public String generateToken(String username , boolean isAccessToken) {
        long expirationTime = isAccessToken ? ACCESS_TOKEN_EXPIRATION : REFRESH_TOKEN_EXPIRATION;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }
}
