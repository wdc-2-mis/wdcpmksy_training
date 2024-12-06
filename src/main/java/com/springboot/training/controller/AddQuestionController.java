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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private UserService userService;
    
    @GetMapping("/addCourseQuestion")
    public String addCourseQuestion(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        AddCourseQuestion question = new AddCourseQuestion();
        //List<LMSTrainingDetails> courses = courseDtlRepository.findAll();
        
        List<LMSTrainingDetails> courses = courseDtlRepository.findCoursesdtl();

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
    public String saveCourseQuestion(
        @Valid @ModelAttribute("question") AddCourseQuestion question,
        BindingResult result, Model model, HttpSession session, @RequestParam(required = false) String action, RedirectAttributes redirectAttributes) {
    	List<LMSTrainingDetails> courses = courseDtlRepository.findCoursesdtl();
        
    	if (result.hasErrors()) {
            Integer checkcourse = question.getCourseId();
            model.addAttribute("courses", courses);
            model.addAttribute("userId", session.getAttribute("userId"));
            model.addAttribute("checkcourse", checkcourse);

            return "addCourseques"; 
        }

        userService.saveaddQuestion(question, session);

        if ("submit".equals(action)) {
        	 userService.updateStatusToComplete(question.getCourseId());
        	 redirectAttributes.addFlashAttribute("successMessage", "Question saved successfully!");
             return "redirect:/addCourseQuestion"; 
        } else {
        	redirectAttributes.addFlashAttribute("successMessage", "Question saved successfully. Please add the next question.");
            Integer checkcourse = question.getCourseId();
            model.addAttribute("courses", courses);
            model.addAttribute("userId", session.getAttribute("userId"));
            model.addAttribute("checkcourse", checkcourse);
            return "redirect:/addCourseQuestion";
        }
    }

    

	 

}
