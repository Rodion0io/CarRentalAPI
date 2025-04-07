package com.example.auth.config;

import com.example.api.constant.ApiPaths;
import com.example.api.dto.RoleDto;
import com.example.auth.jwt.JwtAuthFilter;
import com.example.constant.RolesName;
import lombok.AllArgsConstructor;
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

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Пока отрубил свагер
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                ApiPaths.REGISTRATION,
                                ApiPaths.LOGIN,
                                ApiPaths.REFRESH,
                                "/error"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, ApiPaths.LOGIN, ApiPaths.REFRESH, ApiPaths.REGISTRATION).permitAll()
                        .requestMatchers(HttpMethod.GET, ApiPaths.PERSONAL_PROFILE, ApiPaths.USER_ROLES_PATH).authenticated()
                        .requestMatchers(HttpMethod.GET, ApiPaths.USERS_LIST).hasAnyAuthority(RolesName.ADMIN.toString())
                        .requestMatchers(HttpMethod.PUT, ApiPaths.ADD_ROLE, ApiPaths.BLOCK, ApiPaths.UNBLOCK).hasAnyAuthority(RolesName.ADMIN.toString())
                        .requestMatchers(HttpMethod.POST, ApiPaths.LOGOUT).authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:9000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}