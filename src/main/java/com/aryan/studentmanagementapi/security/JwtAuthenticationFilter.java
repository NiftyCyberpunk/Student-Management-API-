package com.aryan.studentmanagementapi.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aryan.studentmanagementapi.service.CustomUserDetailsService;
import com.aryan.studentmanagementapi.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        return request.getServletPath().startsWith("/auth/");
    }
    
    @Override
    protected void doFilterInternal(
        jakarta.servlet.http.HttpServletRequest request, 
        jakarta.servlet.http.HttpServletResponse response,
        jakarta.servlet.FilterChain filterChain) 

        throws java.io.IOException, jakarta.servlet.ServletException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        String username = jwtService.extractUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isTokenValid(token, userDetails.getUsername()) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
            
        filterChain.doFilter(request, response);
    }
}
