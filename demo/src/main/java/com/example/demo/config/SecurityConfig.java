package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/demo/createUser").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll() // Доступ к Swagger UI
                        .requestMatchers("/v3/api-docs/**").permitAll() // Доступ к документации Swagger
                        .requestMatchers("/api/demo/admin/**").hasRole("admin")
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {
                    // Здесь можно указать дополнительные настройки для базовой аутентификации, если необходимо
                })
                .csrf(csrf -> csrf.disable()); // Отключаем CSRF для упрощения работы с API-клиентами

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Настройка пользователей с их ролями
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("password123")).roles("admin")
                .and()
                .withUser("user").password(passwordEncoder().encode("password123")).roles("user");

        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
