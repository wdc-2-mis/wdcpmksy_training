package com.springboot.training.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.training.dto.CourseDetails;
import com.springboot.training.dto.QuestionDTO;
import com.springboot.training.dto.UserAnswerDTO;
import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.entity.LmsUserQuestionAnswer;
import com.springboot.training.repository.CourseDtlRepository;
import com.springboot.training.repository.LmsTrainingQuestionRepository;
import com.springboot.training.repository.UserQuestionAnswerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class TestController {
	
	@Autowired
	private CourseDtlRepository courseDtlRepository;
	
	@Autowired
	private LmsTrainingQuestionRepository questionRepository;
	
	@Autowired
	private UserQuestionAnswerRepository userQuestionAnswerRepository;
	
	@GetMapping("/getTest")
    public String getTest(HttpSession session, Model model){
		
		String userId = (String) session.getAttribute("userId"); 
		 List<LMSTrainingDetails> courses = courseDtlRepository.findAll();
		 model.addAttribute("courses", courses);
		 model.addAttribute("userId", userId);
		return "getTestt";
    }

	@ResponseBody
    @GetMapping("/getTesstt")
    public CourseDetails getTesstt(@RequestParam Integer trainingId) {
		
		LMSTrainingDetails course = courseDtlRepository.findById(trainingId).orElse(null);
		
        if (course != null) {
        	CourseDetails dto = new CourseDetails();
            dto.setCdesc(course.getCourse_description());
            dto.setPassm("5");
            dto.setTotquest(course.getNoof_question().toString());
           
            return dto;
        }

        return null;
    }
	  
	@ResponseBody
	@GetMapping("/getquestions")
	public List<QuestionDTO> getQuestions(@RequestParam Integer trainingId) {
	    List<LmsTrainingQuestion> questions = questionRepository.findByTrainingId(trainingId);
	    List<QuestionDTO> dtos = new ArrayList<>();
	    for (LmsTrainingQuestion ques : questions) {
	        QuestionDTO dto = new QuestionDTO();
	        dto.setQuestionId(ques.getQuestionId());
	        dto.setTrainingId(ques.getTrainingId());
	        dto.setUserRegId(ques.getUserRegId());
	        dto.setQuestionDescription(ques.getQuestionDescription());
	        dto.setOption1(ques.getOption1());
	        dto.setOption2(ques.getOption2());
	        dto.setOption3(ques.getOption3());
	        dto.setOption4(ques.getOption4());
	        dtos.add(dto);
	    }
	    return dtos;
	}
	
	@ResponseBody
	@PostMapping("/submitTest")
	public String submitTest(
	    @RequestBody List<UserAnswerDTO> answers,
	    @RequestParam Integer trainingId, 
	    HttpSession session
	) {
	    String userId = (String) session.getAttribute("userId");
	    Integer userRegId =  Integer.parseInt(session.getAttribute("regid").toString());

	    System.out.println("Received answers for Training ID: " + trainingId);
	    

	    for (UserAnswerDTO answer : answers) {
	        LmsUserQuestionAnswer userAnswer = new LmsUserQuestionAnswer();
	        userAnswer.setQuestionId(answer.getQuestionId());
	        userAnswer.setUserAnswer(answer.getUserAnswer());
	        userAnswer.setUserRegId(userRegId);
	        userAnswer.setTrainingId(trainingId); 
	        userAnswer.setStatus("C");
//	        userAnswer.setRequestedIp("127.0.0.1");
	        userAnswer.setCreatedBy(userId);
	        
	        userAnswer.setCreatedDate(LocalDateTime.now());

	        userQuestionAnswerRepository.save(userAnswer);
	    }

	    return "Answers submitted successfully";
	}


//	@ResponseBody
//	@PostMapping("/submitTest")
//	public String submitTest(@RequestBody List<UserAnswerDTO> answers, HttpSession session) {
//		 String userId = (String) session.getAttribute("userId");
////		    Integer userRegId = Integer.parseInt(userId);
//		    Integer trainingId=1 ;
//		    Integer userRegId=1 ;
////	    // Log the incoming answers to verify the data
//	    System.out.println("Received answers: ");
//	    for (UserAnswerDTO answer : answers) {
//	        System.out.println("Question ID: " + answer.getQuestionId() + ", Answer: " + answer.getUserAnswer());
//	    }
//
//	    for (UserAnswerDTO answer : answers) {
//	        LmsUserQuestionAnswer userAnswer = new LmsUserQuestionAnswer();
//	        userAnswer.setQuestionId(answer.getQuestionId());
//	        userAnswer.setUserAnswer(answer.getUserAnswer());
//	        userAnswer.setUserRegId(userRegId);
//	        userAnswer.setTrainingId(answer.getTrainingId());
//	        userAnswer.setStatus("C");
//	        userAnswer.setRequestedIp("127.0.0.1");
//	        userAnswer.setCreatedBy(userId);
//	        userAnswer.setCreatedDate(LocalDateTime.now());
//
//	        userQuestionAnswerRepository.save(userAnswer);
//	    }
//
//	    return "Answers submitted successfully";
//	}
}