package com.aryan.studentmanagementapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers(HttpMethod.GET, "/students/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                    .requestMatchers(HttpMethod.POST, "/students/**").hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers(HttpMethod.PATCH, "/students/**").hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers(HttpMethod.DELETE, "/students/**").hasRole("ADMIN")
                    .requestMatchers("/branches").hasRole("ADMIN")
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated()
            );
        
        return http.build();
    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
