package com.example.tuan3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // cho phép tất cả API
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}