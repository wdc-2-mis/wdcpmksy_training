package com.springboot.training.security;

import com.springboot.training.entity.User;
import com.springboot.training.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OtpAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    private UserRepository userRepository;
    
    public OtpAuthenticationFilter(UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }
    
    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            String email = request.getParameter("username");
            String otp = request.getParameter("otp");

            if (email == null || otp == null) {
                throw new AuthenticationServiceException("Email and OTP must be provided.");
            }

            User user = userRepository.findByEmail(email);
            if (user == null || !user.getOtp().equals(otp) || 
                user.getOtpExpirationTime().isBefore(LocalDateTime.now())) {
                throw new AuthenticationServiceException("Invalid OTP or OTP expired.");
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    email, otp, new ArrayList<>());

            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Authentication failed: " + e.getMessage());
        }
    }

    
    

}
