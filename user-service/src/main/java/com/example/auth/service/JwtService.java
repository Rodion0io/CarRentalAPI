//package com.example.auth.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//import java.util.function.Function;
//
//@Service
//@RequiredArgsConstructor
//public class JwtService {
//    @Autowired
//    private final UserDetailsService userDetailsService;
//
//    @Value("${jwt.accessSecret}")
//    private String ACCESS_SECRETE;
//
//    @Value("${jwt.refreshSecrete}")
//    private String REFRESH_TOKEN;
//
//    public String generateAccessToken(String userId, String login, List<String> roles) {
//        return Jwts.builder()
//                .setSubject(userId)
//                .claim("roles", roles)
//                .claim("login", login)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 1 day
//                .signWith(getSignInKey(ACCESS_SECRETE), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String generateRefreshToken(String userId, String login) {
//        return Jwts.builder()
//                .setSubject(userId)
//                .claim("login", login)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 1 day
//                .signWith(getSignInKey(REFRESH_TOKEN), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    private Claims extractAllClaimsFromAccess(String token, boolean isAccess) {
//        return Jwts.parser().
//                setSigningKey(getSignInKey(isAccess ? ACCESS_SECRETE : REFRESH_TOKEN))
//                .build().parseClaimsJws(token)
//                .getBody();
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, boolean isAccess){
//        final Claims claims = extractAllClaimsFromAccess(token, isAccess);
//        return claimsResolver.apply(claims);
//    }
//
//    public String extractLogin(String token, boolean isAccess) {
//        return extractClaim(token, claims -> claims.get("login", String.class), isAccess);
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails, boolean isAccess){
//        final String username = extractLogin(token, isAccess);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, isAccess));
//    }
//
//    private Key getSignInKey(String key) {
//        byte[] keyBytes = Decoders.BASE64.decode(key);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public UUID extractUserId(String token, boolean isAccess){
//        return UUID.fromString(extractClaim(token, claims -> claims.get("sub", String.class), isAccess));
//    }
//
//    public boolean isTokenExpired(String token, boolean isAccess) {
//        return extractExpiration(token, isAccess).before(new Date());
//    }
//
//    private Date extractExpiration(String token, boolean isAccess) {
//
//        return extractClaim(token, Claims::getExpiration, isAccess);
//    }
//}
