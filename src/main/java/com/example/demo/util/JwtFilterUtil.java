package com.example.demo.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilterUtil extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilterUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring(7);

            if(token.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtUtil.isTokenValid(token)) {
                Claims payload = jwtUtil.getPayload(token);

                List<String> roles = payload.get("roles", List.class);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                var authentication = new UsernamePasswordAuthenticationToken(payload.getSubject(), "", authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
