package com.example.ticketingsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // Allow CORS for all endpoints
                    .allowedOrigins("http://localhost:3008") // Allow requests from frontend URL
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific methods
                    .allowedHeaders("*") // Allow all headers
                    .allowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)
        }
    }

