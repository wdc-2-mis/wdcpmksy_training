package com.springboot.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.repository.CourseDtlRepository;

import jakarta.servlet.http.HttpSession;



@Controller
public class AddQuestionController {

	@Autowired
	private CourseDtlRepository courseDtlRepository;
	
	@GetMapping("/addCourseQuestion")
    public String addCourseQuestion(HttpSession session, Model model){
		String userId = (String) session.getAttribute("userId"); 
		 List<LMSTrainingDetails> courses = courseDtlRepository.findAll();
		 model.addAttribute("courses", courses);
		 model.addAttribute("userId", userId);
		return "addCourseques";
    }
}
