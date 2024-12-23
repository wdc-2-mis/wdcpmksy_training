package com.springboot.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.training.entity.User;
import com.springboot.training.repository.UserRepository;
import com.springboot.training.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DownloadCertificate {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository ur;
	
	@GetMapping("/listofUserSearch")
    public String showCourseDetail(HttpSession session, Model model){
		
		
		
		try {
		
    	 String userId = (String) session.getAttribute("userId"); 
    	 if (userId == null) { 
    		 return "redirect:/login?error=session"; 
    		 }
    	 Integer regid =  Integer.parseInt(session.getAttribute("regid").toString());
     	 String userType = (String) session.getAttribute("userType");
    //	 CourseDetails curl = new CourseDetails();
	//	 model.addAttribute("curl" , curl);
     //	 List<User>  user= userService.listofAllUsers();
     	 List<User>  user=  ur.findAll();                  
     	            
     	 model.addAttribute("user", user);
		 model.addAttribute("userId", userId);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("kdy");
		}
		
		
		 return "downloadCertificate";
		 
    }

}
