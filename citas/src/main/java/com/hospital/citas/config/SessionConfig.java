package com.hospital.citas.config;


import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class SessionConfig {

    @Bean
    @RequestScope
    public HttpSession httpSession(HttpSession session) {
        return session;
    }
}