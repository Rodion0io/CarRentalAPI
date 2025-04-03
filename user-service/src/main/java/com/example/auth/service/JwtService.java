package com.example.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Autowired
    private final UserDetailsService userDetailsService;

    @Value("${jwt.accessSecret}")
    private String ACCESS_SECRETE;

    @Value("${jwt.refreshSecrete}")
    private String REFRESH_TOKEN;

    public String generateAccessToken(String userId, String login, List<String> roles) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("roles", roles)
                .claim("login", login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 1 day
                .signWith(getSignInKey(ACCESS_SECRETE), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String userId, String login) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("login", login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 1 day
                .signWith(getSignInKey(REFRESH_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaimsFromAccess(String token) {
        return Jwts.parser().
                setSigningKey(getSignInKey(ACCESS_SECRETE))
                .build().parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaimsFromAccess(token);
        return claimsResolver.apply(claims);
    }

    public String extractLogin(String token) {
        return extractClaim(token, claims -> claims.get("login", String.class));
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractLogin(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Key getSignInKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public UUID extractUserId(String token){
        return UUID.fromString(extractClaim(token, claims -> claims.get("sub", String.class)));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }
}
