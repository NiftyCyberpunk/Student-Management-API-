package com.aryan.studentmanagementapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aryan.studentmanagementapi.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/students").hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers(HttpMethod.GET, "/students/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                    .requestMatchers(HttpMethod.POST, "/students/*/update-request").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                    .requestMatchers(HttpMethod.POST, "/students/**").hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers(HttpMethod.PATCH, "/students/**").hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers(HttpMethod.DELETE, "/students/**").hasRole("ADMIN")
                    .requestMatchers("/branches").hasRole("ADMIN")
                    .requestMatchers("/admin/update-requests/**").hasAnyRole("ADMIN", "TEACHER")
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> 
                exception.authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                )
            );
        return http.build();
    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        
        return configuration.getAuthenticationManager();
    }
}
