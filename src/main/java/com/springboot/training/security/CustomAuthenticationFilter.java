package com.springboot.training.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", "POST"));
        setAuthenticationManager(authenticationManager);
    }

    /**
     * 🔴 CRITICAL FIX
     * Exclude static & public resources from authentication filter
     */
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/pictures/")
            || path.startsWith("/img/")
            || path.startsWith("/images/")
            || path.startsWith("/css/")
            || path.startsWith("/js/")
            || path.startsWith("/sound/")
            || path.startsWith("/videos/")
            || path.equals("/pictures")
            || path.equals("/videos");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        String authType = request.getParameter("authType");
        String username = request.getParameter("username");
        String credentials = "otp".equals(authType)
                ? request.getParameter("otp")
                : request.getParameter("password");

        if (username == null || credentials == null || credentials.isEmpty()) {
            response.sendRedirect("/login?error");
            return null;
        }

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, credentials);

        try {
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (AuthenticationException e) {
            if ("otp".equals(authType)) {
                response.sendRedirect("/login?otperror");
            } else {
                response.sendRedirect("/login?error");
            }
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);
        request.getSession().setAttribute(
                "SPRING_SECURITY_CONTEXT",
                SecurityContextHolder.getContext()
        );

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
