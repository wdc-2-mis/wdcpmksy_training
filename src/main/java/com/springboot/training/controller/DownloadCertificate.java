package com.springboot.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.training.dto.CourseCompletionDetailsDto;
import com.springboot.training.entity.User;
import com.springboot.training.repository.CousreCompletionDetailsRepository;
import com.springboot.training.repository.UserRepository;
import com.springboot.training.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DownloadCertificate {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository ur;
	
	@Autowired
	CousreCompletionDetailsRepository courseCompDtlRepo;
	
	@GetMapping("/listofUserSearch/{id}")
    public String showCourseDetail(HttpSession session, @PathVariable("id") int id, Model model){
		
		
		
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
//     	 List<User>  user=  ur.getUserDetailsByTrainingId(id);  
     	 List<CourseCompletionDetailsDto> user = courseCompDtlRepo.getCourseCompletionDetails(id);
     	            
     	 model.addAttribute("user", user);
		 model.addAttribute("userId", userId);
		 model.addAttribute("trainingId",id);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("kdy");
		}
		
		
		 return "downloadCertificate";
		 
    }

}
