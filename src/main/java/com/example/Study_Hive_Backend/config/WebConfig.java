package com.example.Study_Hive_Backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5173","http://localhost:5173") // Frontend URL
                .allowedMethods("GET", "POST", "PUT", "PATCH" , "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    }

