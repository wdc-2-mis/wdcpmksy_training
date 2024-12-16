package com.springboot.training.security;

import com.springboot.training.entity.User;
import com.springboot.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp = authentication.getCredentials().toString();

        User user = userService.findUserByEmail(username);

        if (user == null || !otp.equals(user.getOtp()) || user.getOtpExpirationTime().isBefore(LocalDateTime.now())) {
            throw new BadCredentialsException("Invalid OTP or expired.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        String userType = user.getUser_type().toUpperCase();
        
        if ("ADMIN".equals(userType)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if ("USER".equals(userType)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if (authorities.isEmpty()) {
            throw new BadCredentialsException("User has no assigned roles.");
        }

        return new UsernamePasswordAuthenticationToken(username, otp, authorities);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
