package com.springboot.training.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.springboot.training.entity.User;
import com.springboot.training.service.UserService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
	private final UserService userService;
	
	public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }
	
	 
	 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // Check user roles and redirect accordingly
    	
    	String email = authentication.getName();
    	User user = userService.findUserByEmail(email);
    	
    	if (user != null) {
            // Set user attributes in session
            request.getSession().setAttribute("regid", user.getUser_reg_id());
            request.getSession().setAttribute("userId", user.getUser_id());
            request.getSession().setAttribute("userType", user.getUser_type());
        }
    	
    	if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("/showCourseDetail");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            response.sendRedirect("/userlogin");
        } else {
            response.sendRedirect("/login?error"); // Fallback for unexpected roles
        }
    }
}
