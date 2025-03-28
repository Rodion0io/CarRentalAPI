package com.example.auth.jwt;

import com.example.auth.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = checkerToken(request);
            if (token != null){
                createContext(token);
            }
            filterChain.doFilter(request, response);
        }
        catch (JwtException e) {
            response.sendError(401, "Invalid token: " + e.getMessage());
            return;
        }
    }

    private boolean isValidToken(String token){
        boolean isValid = jwtService.isTokenExpired(token);
        return isValid;
    }

    private String checkerToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")){
            token = token.substring(7);
            return token;
        }
        return null;
    }

    private void createContext(String token){
        boolean isValidToken = isValidToken(token);
        String userLogin = jwtService.extractLogin(token);
        if (userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userLogin);
            if (isValidToken){
                UsernamePasswordAuthenticationToken authToken = new  UsernamePasswordAuthenticationToken (userDetails, null , userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }
}
