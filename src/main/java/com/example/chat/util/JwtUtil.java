package com.example.chat.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "replace-with-very-long-secret-key-for-prod-please!";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXP = 1000L * 60 * 60 * 24; // 1 day

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(KEY)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    public static String getUsername(String token) {
        Claims c = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody();
        return c.getSubject();
    }
}
