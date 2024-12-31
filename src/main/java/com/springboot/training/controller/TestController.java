package com.springboot.training.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.springboot.training.entity.LmsUserQuizDetails;
import com.springboot.training.repository.CourseDtlRepository;
import com.springboot.training.repository.LmsTrainingQuestionRepository;
import com.springboot.training.repository.LmsUserQuizDetailsRepository;
import com.springboot.training.repository.UserQuestionAnswerRepository;
import com.springboot.training.service.UserCourseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TestController {
	
	@Autowired
	private CourseDtlRepository courseDtlRepository;
	
	@Autowired
	private LmsTrainingQuestionRepository questionRepository;
	
	@Autowired
	private UserQuestionAnswerRepository userQuestionAnswerRepository;
	
	@Autowired
	private LmsUserQuizDetailsRepository userQuizDetailsRepository;
	
	@GetMapping("/getTest")
	public String getTest(HttpSession session, @RequestParam("training_id") Integer trainingId, Model model) {
	    String userId = (String) session.getAttribute("userId");
	    List<LMSTrainingDetails> courses = new ArrayList<>();
	    LMSTrainingDetails crse = courseDtlRepository.getById(trainingId);

	    courses.add(crse);
	    model.addAttribute("courses", courses);
	    model.addAttribute("userId", userId);
	    model.addAttribute("trainingId", trainingId); 
	    return "getTestt";
	}


	@ResponseBody
    @GetMapping("/getTesstt")
    public CourseDetails getTesstt(@RequestParam Integer trainingId) {
		
		LMSTrainingDetails course = courseDtlRepository.findById(trainingId).orElse(null);
		
        if (course != null) {
        	CourseDetails dto = new CourseDetails();
            dto.setCdesc(course.getCourse_description());
//            dto.setPassm("6");
            dto.setTotquest(course.getNoof_question().toString());
            dto.setDurexam(course.getDuration_exam().toString());
            dto.setTrainingId(trainingId);
            return dto;
        }

        return null;
    }
	  

	@ResponseBody
	@GetMapping("/getquestions")
	public List<QuestionDTO> getQuestions(@RequestParam Integer trainingId) {
	    List<LmsTrainingQuestion> questions = questionRepository.findByTrainingIdAndStatus(trainingId, "C");
	    
	    Collections.shuffle(questions);
	    LMSTrainingDetails courses = courseDtlRepository.findById(trainingId).orElse(null);

	    List<LmsTrainingQuestion> randomQuestions = questions.subList(0, Math.min(courses.getAttempt_question(), questions.size()));
	    
	    List<QuestionDTO> dtos = new ArrayList<>();
	    for (LmsTrainingQuestion ques : randomQuestions) {
	        QuestionDTO dto = new QuestionDTO();
	        dto.setQuestionId(ques.getQuestionId());
	        dto.setTrainingId(ques.getTrainingId());
	        dto.setUserRegId(ques.getUserRegId());
	        dto.setQuestionDescription(ques.getQuestionDescription());
	        dto.setOption1(ques.getOption1());
	        dto.setOption2(ques.getOption2());
	        dto.setOption3(ques.getOption3());
	        dto.setOption4(ques.getOption4());
	        dto.setQuestionMarks(ques.getQuestionMarks());
	        dtos.add(dto);
	    }
	    return dtos;
	}
	
	@ResponseBody
	@PostMapping("/submitTest")
	public String submitTest(
	        @RequestBody List<UserAnswerDTO> answers,
	        @RequestParam Integer trainingId,
	        HttpSession session) {

	    // Retrieve user details from the session
	    String userId = (String) session.getAttribute("userId");
	    Integer userRegId = Integer.parseInt(session.getAttribute("regid").toString());

	    // Fetch all questions for the given training ID
	    List<LmsTrainingQuestion> allQuestions = questionRepository.findByTrainingIdAndStatus(trainingId, "C");
	    LMSTrainingDetails courses = courseDtlRepository.findById(trainingId).orElse(null);
	    List<LmsTrainingQuestion> randomQuestions = allQuestions.subList(0, Math.min(courses.getAttempt_question(), allQuestions.size()));
	    int totalQuestions = randomQuestions.size(); // Total available questions for the training

	    // Variables to track quiz summary
	    int attemptedQuestions = answers.size(); // Number of questions answered by the user
	    int correctAnswers = 0;
	    int marksObtained = 0;
	    int totalMarks = 0;
	    int marks=0;

	    for (UserAnswerDTO answer : answers) {
	        // Save each user's answer
	        LmsUserQuestionAnswer userAnswer = new LmsUserQuestionAnswer();
	        userAnswer.setQuestionId(answer.getQuestionId());
	        userAnswer.setUserAnswer(answer.getUserAnswer());
	        userAnswer.setUserRegId(userRegId);
	        userAnswer.setTrainingId(trainingId); 
	        userAnswer.setStatus("C");
	        userAnswer.setCreatedBy(userId);
	        userAnswer.setCreatedDate(LocalDateTime.now());

	        userQuestionAnswerRepository.save(userAnswer);

	        // Calculate correct answers and marks
	        LmsTrainingQuestion question = questionRepository.findById(answer.getQuestionId()).orElse(null);
	        if (question != null) {
	            totalMarks += question.getQuestionMarks(); // Add to total marks for all questions
	            if (question.getOptionAnswer().equals(answer.getUserAnswer())) {
	                correctAnswers++;
	                marksObtained += question.getQuestionMarks(); // Add to obtained marks for correct answers
	            }
	        }
	        marks = question.getQuestionMarks();
	    }
	    LocalDateTime now = LocalDateTime.now();
	    Date updatedDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

	    // Calculate percentage
	    double percentage = (totalMarks > 0) ? ((double) marksObtained / (totalQuestions*marks)) * 100 : 0;

	    // Determine pass or fail status
	    String quizStatus = (percentage >= 40) ? "P" : "F";

	    // Save quiz details
	    LmsUserQuizDetails quizDetails = new LmsUserQuizDetails();
	    quizDetails.setTrainingId(trainingId);
	    quizDetails.setUserRegId(userRegId);
	    quizDetails.setMarksObtained(marksObtained); // Marks for correct answers
	    quizDetails.setQuestionAttempt(attemptedQuestions); // Number of questions attempted
	    quizDetails.setStatus(quizStatus); 
	    quizDetails.setCreatedDate(new Date());
	    quizDetails.setCreatedBy(userId);
	    quizDetails.setUpdatedBy(userId);
	    quizDetails.setUpdatedDate(new Date());
	    quizDetails.setTotalMarks(totalQuestions*marks); // Total marks for all questions

	    userQuizDetailsRepository.save(quizDetails);

	    System.out.println("Total Questions: " + totalQuestions);
	    System.out.println("Attempted Questions: " + attemptedQuestions);
	    System.out.println("Correct Answers: " + correctAnswers);
	    System.out.println("Marks Obtained: " + marksObtained);
	    System.out.println("Total Marks: " + totalMarks);

	    return "Answers submitted successfully and quiz details saved.";
	}


}