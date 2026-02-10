package com.springboot.training.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.springboot.training.entity.User;
import com.springboot.training.repository.UserRepository;
import com.springboot.training.service.UserService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Autowired
    UserRepository repo;

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        Integer regid = repo.findregid(email);

        System.out.println("hello regid:" + regid);

        if (user != null) {
            HttpSession session = request.getSession(true); // Create session after successful login
            session.setAttribute("email", email);
            System.out.println("Session ID after login: " + session.getId());
            // Redirect to a page where we can trigger a POST request
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                response.setContentType("text/html");
                response.getWriter().write(
                        "<html><body>" +
                                "<form id='redirectForm' method='POST' action='/getCourseDetail'>" +
                                "<input type='hidden' name='regid' value='" + regid + "' />" +
                                "</form>" +
                                "<script type='text/javascript'>" +
                                "document.getElementById('redirectForm').submit();" +
                                "</script>" +
                                "</body></html>"
                );
                
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                // Create a small HTML form with POST method to submit the `regid`
                response.setContentType("text/html");
                response.getWriter().write(
                        "<html><body>" +
                                "<form id='redirectForm' method='POST' action='/userCourse'>" +
                                "<input type='hidden' name='regid' value='" + regid + "' />" +
                                "</form>" +
                                "<script type='text/javascript'>" +
                                "document.getElementById('redirectForm').submit();" +
                                "</script>" +
                                "</body></html>"
                );
            }
        } else {
            response.sendRedirect("/login?error");
        }
    }
}
