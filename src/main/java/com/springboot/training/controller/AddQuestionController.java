package com.springboot.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.springboot.training.entity.LmsTrainingQuestion;
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
    public String addCourseQuestion(@RequestParam("trainingId") Integer trainingId, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        AddCourseQuestion question = new AddCourseQuestion();
        LMSTrainingDetails course = courseDtlRepository.findById(trainingId).orElse(null);
        int questionCount = trainingQuestionRepository.countQuestionsByTrainingId(trainingId);
		/* List<LMSTrainingDetails> courses = courseDtlRepository.findCoursesdtl(); */

		/* model.addAttribute("courses", courses); */
        model.addAttribute("questionsno", questionCount+1);
        model.addAttribute("coursename", course.getCourse_name());
        model.addAttribute("cousedesc", course.getCourse_description());
        model.addAttribute("noofques", course.getNoof_question());
        model.addAttribute("question", question);
        model.addAttribute("userId", userId);
        model.addAttribute("trainingId", trainingId);
        
        if(questionCount == course.getNoof_question())
        {
        	return "redirect:/viewAllQuestions?trainingId=" + trainingId;
        }
            return "addCourseques";
        
        }

   	
    @PostMapping("/savedraftCourseQuestion")
    public String savedraftCourseQuestion(@Valid @ModelAttribute("question") AddCourseQuestion question,BindingResult result,Model model,
    		@RequestParam("trainingId") Integer trainingId, HttpSession session,@RequestParam(required = false) String action,RedirectAttributes redirectAttributes) {

         LMSTrainingDetails course = courseDtlRepository.findById(trainingId).orElse(null);        
        int questionCount = trainingQuestionRepository.countQuestionsByTrainingId(trainingId);

        if (result.hasErrors()) {
            setupModelAttributes(model, course, questionCount, trainingId, session, question);
            return "addCourseques"; 
        }

        userService.saveaddQuestion(question, session, trainingId);
        
        if ("submit".equalsIgnoreCase(action)) {
        	return "redirect:/viewAllQuestions?trainingId=" + trainingId;
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Question saved successfully. Please add the next question.");
            return "redirect:/addCourseQuestion?trainingId="+trainingId;
        }

        
    }

    private void setupModelAttributes(Model model, LMSTrainingDetails course, int questionCount, 
                                      Integer trainingId, HttpSession session, AddCourseQuestion question) {
        model.addAttribute("questionsno", questionCount + 1);
        model.addAttribute("coursename", course.getCourse_name());
        model.addAttribute("cousedesc", course.getCourse_description());
        model.addAttribute("noofques", course.getNoof_question());
        model.addAttribute("question", question);
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("trainingId", trainingId);
    }

    		
    
    
    @GetMapping("/viewAllQuestions")
    public String viewAllQuestions(@RequestParam("trainingId") Integer trainingId,  @RequestParam(defaultValue = "1") int page,
    		Model model, HttpSession session) {
    	int pageSize = 10;  // Show 10 questions per page
        PageRequest pageable = PageRequest.of(page - 1, pageSize);
        String status = courseDtlRepository.getTrainingStatus(trainingId);
    	LMSTrainingDetails course = courseDtlRepository.findById(trainingId).orElse(null); 
    	
    	Page<LmsTrainingQuestion> questionsPage = trainingQuestionRepository.findByTrainingId(trainingId, pageable);

    	model.addAttribute("course", course);
        model.addAttribute("questionsPage", questionsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", questionsPage.getTotalPages());
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("trainingId", trainingId);
        model.addAttribute("status", status);
        model.addAttribute("noofques", course.getNoof_question());
        return "viewQuestions";  
    }
    
    @PostMapping("/saveCourseQuestion")
    public String saveCourseQuestion(@RequestParam("trainingId") Integer trainingId,Model model) {
    	
    	courseDtlRepository.updateTrainingStatus(trainingId, "C");
    	trainingQuestionRepository.updateQuestionStatus(trainingId, "C");
        
        return "redirect:/getCourseDetail";
    }

    

	 

}
