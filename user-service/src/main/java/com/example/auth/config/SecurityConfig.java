package com.example.auth.config;

import com.example.auth.jwt.JwtTokenFilter;
import com.example.constant.RolesName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем все что нужно для Swagger
                        .requestMatchers(
                                "/",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()
                        // Разрешаем auth endpoints
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        // Разрешаем error endpoint
                        .requestMatchers("/error").permitAll()
                        // Настройка доступа для USER
                        .requestMatchers(HttpMethod.POST, "/request/**").hasAuthority(RolesName.USER.toString())
                        .requestMatchers(HttpMethod.GET, "/{userId}/grant-role").hasAuthority(RolesName.USER.toString())
                        .requestMatchers(HttpMethod.PATCH, "/request/{id}/status").hasAuthority(RolesName.USER.toString())
                        .requestMatchers(HttpMethod.PUT, "/api/users/{userId}/grant-dean-role").hasAuthority(RolesName.USER.toString())
                        .requestMatchers(HttpMethod.GET, "/request_info/**").hasAnyAuthority(RolesName.USER.toString())
                        .requestMatchers(HttpMethod.GET, "/request_list").hasAnyAuthority(RolesName.USER.toString())
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority(RolesName.USER.toString())
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}