package com.example.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    @Value("${jwt.accessSecret}")
    private String ACCESS_SECRET_KEY;

    @Value("${jwt.refreshSecrete}")
    private String REFRESH_SECRETE_KEY;

    public String generateAccessToken(String userId, List<String> listRoles, String login){
        return Jwts.builder()
                .setSubject(userId)
                .claim("login", login)
                .claim("roles", listRoles)
                .setIssuedAt( new  Date (System.currentTimeMillis()))
                .setExpiration( new  Date (System.currentTimeMillis() + 1000 * 60 * 24 )) //пока 1 день
                .signWith(getAccessSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String userId, String login){
        return Jwts.builder()
                .setSubject(userId)
                .claim("login", login)
                .setIssuedAt( new  Date (System.currentTimeMillis()))
                .setExpiration( new  Date (System.currentTimeMillis() + 1000 * 60 * 24 )) //пока 1 день
                .signWith(getRefreshSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getAccessSignInKey(){
        Key result = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ACCESS_SECRET_KEY));
        return result;
    }

    private Key getRefreshSignInKey(){
        Key result = Keys.hmacShaKeyFor(Decoders.BASE64.decode(REFRESH_SECRETE_KEY));
        return result;
    }

    private Claims extractAllClaims(String token){
        try{
            return Jwts.parser().setSigningKey(getAccessSignInKey()).build().parseClaimsJws(token).getBody();
        }
        catch (Exception error){
            log.error("Не валидный токен: {}", error.getMessage());
//            Временно так ошибку создаем
            throw new Error("Не валидный токен");
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<String> extractRoles(String token){
        try{
            return extractClaim(token, claims -> claims.get("roles", List.class));
        }
        catch (Exception error){
            log.error("Не валидный токен: {}", error.getMessage());
//            Временно так ошибку создаем
            throw new Error("Не валидный токен");
        }
    }

    public String extractLogin(String token){
        try{
            return extractClaim(token, claims -> claims.get("login", String.class));
        }
        catch (Exception error){
            log.error("Не валидный токен: {}", error.getMessage());
//            Временно так ошибку создаем
            throw new Error("Не валидный токен");
        }
    }



    private Date extractExpireDate(String token){
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration;
    }

    public boolean isTokenExpired(String token){
        return extractExpireDate(token).before(new Date());
    }

}
