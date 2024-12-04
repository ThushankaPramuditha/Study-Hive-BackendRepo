package com.example.Study_Hive_Backend.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/v1/auth/**").permitAll()

                        .requestMatchers("/api/questions/**").permitAll()
                        .requestMatchers("/api/profiles/**").authenticated()
//                         .requestMatchers("api/questions/**").permitAll()
                        .requestMatchers("api/studyrooms/**").permitAll()
                        .requestMatchers("/api/notifications/**").permitAll()

                        // Allow access to GET /api/profiles/{userId} without authentication
                        .requestMatchers(HttpMethod.GET, "/api/profiles/{userId}").permitAll()

                        // Ensure POST, PUT, DELETE require authentication
                        .requestMatchers(HttpMethod.GET, "/api/profiles/me").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/profiles/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/profiles/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/profiles/**").authenticated()
                        .requestMatchers("api/statistics/**").permitAll()

                        // Allow access to other routes
                        .requestMatchers("/api/questions/**").permitAll()
                        .requestMatchers("/api/studyrooms/**").permitAll()
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .requestMatchers("/api/reports/7days").permitAll()
                        .requestMatchers("/api/admin/dashboard-stats").permitAll()
                        .requestMatchers("/api/communities/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/inquiries/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/inquiries/all").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/inquiries/*/reply").permitAll() // Use wildcard to catch any inquiryId


                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
