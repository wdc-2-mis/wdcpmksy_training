package com.springboot.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.training.dto.AddCourseQuestion;
import com.springboot.training.dto.CourseDetails;
import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.repository.CourseDtlRepository;
import com.springboot.training.repository.LmsTrainingQuestionRepository;
import com.springboot.training.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class AddQuestionController {

    @Autowired
    private CourseDtlRepository courseDtlRepository;

    @Autowired
    private LmsTrainingQuestionRepository trainingQuestionRepository; 

    private UserService userService;
    
    @GetMapping("/addCourseQuestion")
    public String addCourseQuestion(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        AddCourseQuestion question = new AddCourseQuestion();
        List<LMSTrainingDetails> courses = courseDtlRepository.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("question", question);
        model.addAttribute("userId", userId);

        return "addCourseques";
    }

    @ResponseBody
    @GetMapping("/getCourseDetails")
    public CourseDetails getCourseDetails(@RequestParam Integer trainingId) {
        LMSTrainingDetails course = courseDtlRepository.findById(trainingId).orElse(null);

        if (course != null) {
            CourseDetails dto = new CourseDetails();
            dto.setCdesc(course.getCourse_description());
            dto.setTotquest(course.getNoof_question() != null ? course.getNoof_question().toString() : "0");
            return dto;
        }
        return null;
    }

    @ResponseBody
    @GetMapping("/getNextQuestionNumber")
    public int getNextQuestionNumber(@RequestParam Integer trainingId) {
        int questionCount = trainingQuestionRepository.countQuestionsByTrainingId(trainingId);

        return questionCount + 1;
    }

	
	  @PostMapping("/saveCourseQuestion") 
	  public String saveCourseQuestion(@Valid @ModelAttribute("question") AddCourseQuestion question, BindingResult result,Model model,HttpSession session,
	  
	  @RequestParam(required = false) String action) {
	  
	  if (result.hasErrors()) { 
	  Integer checkcourse = question.getCourseId();
	  List<LMSTrainingDetails> courses = courseDtlRepository.findAll();
	  model.addAttribute("courses", courses); 
	  model.addAttribute("userId", session.getAttribute("userId")); 
	  model.addAttribute("checkcourse", checkcourse);
	  
	  return "addCourseques"; } 
	   //userService.saveaddQuestion(question, session);
	  if ("submit".equals(action)) 
	  { return "redirect:/addCourseQuestion?success";
	  } else 
	  { Integer checkcourse = question.getCourseId();
	  List<LMSTrainingDetails> courses = courseDtlRepository.findAll();
	  model.addAttribute("courses", courses); model.addAttribute("userId",
	  session.getAttribute("userId")); model.addAttribute("checkcourse",checkcourse); 
	  return "addCourseques?success"; }
	  
	  }
	 

    


}
