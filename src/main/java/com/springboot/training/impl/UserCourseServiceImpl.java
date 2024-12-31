package com.springboot.training.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.entity.LmsUserQuestionAnswer;
import com.springboot.training.entity.LmsUserQuizDetails;
import com.springboot.training.repository.LmsUserQuizDetailsRepository;
import com.springboot.training.repository.QuestionRepository;
import com.springboot.training.repository.UserCourseRepository;
import com.springboot.training.repository.UserQuestionAnswerRepository;
import com.springboot.training.service.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {

	@Autowired
	private UserCourseRepository ucRepo;

	@Autowired
	private QuestionRepository tqRepo;
	
	@Autowired
	private UserQuestionAnswerRepository uaRepo;
	
	@Autowired
	private LmsUserQuizDetailsRepository qdRepo;

	@Override
	public List<LMSTrainingDetails> getUserCourse() {
		return ucRepo.findAll();
	}

	@Override
	public Optional<LMSTrainingDetails> getCourseById(Integer Training_id) {
		return ucRepo.findById(Training_id);
	}

	@Override
	public Integer calTotalMarks(Integer Training_id) {
		LMSTrainingDetails course = ucRepo.findById(Training_id).orElseThrow();
		List<LmsTrainingQuestion> ques = tqRepo.findByTrainingId(Training_id);
		Integer marks = 0;
		Integer questions = 0;
//		Integer i=0;
//		for (LmsTrainingQuestion temp : ques) {
//			i=i+temp.getQuestionMarks();
//		}
//		return i;		

//		return ques.stream().map(LmsTrainingQuestion::getQuestionMarks).reduce(0, Integer::sum);
		
//		if (ques.isEmpty()) {
//		      throw new RuntimeException("No questions found for the course");
//		    }
		    
		    marks = ques.get(0).getQuestionMarks();
		    questions = course.getAttempt_question();
		    return marks * questions;
		
	}

	@Override
	public boolean isUserPassed(Integer userRegId, Integer courseId) {
		Integer userScore = ucRepo.getUserScore(userRegId, courseId);
		Integer minPassMarks = ucRepo.getMinPassMarks(courseId);

		return userScore != null && userScore >= minPassMarks;
	}

	@Override
	public Integer getQuestionsAttempted(Integer trainingId, Integer userRegId) {
		return uaRepo.getQuestionsAttempted(trainingId, userRegId);
	}

	@Override
	public Integer calculateMarksObtained(Integer courseId, Integer userRegId) {
		List<LmsTrainingQuestion> ques = tqRepo.findByTrainingId(courseId);
        List<LmsUserQuestionAnswer> userAnswers = uaRepo.findUserAnswers(courseId, userRegId);

        Integer marksObtained = 0;
        
        for (LmsTrainingQuestion q : ques) {
            for (LmsUserQuestionAnswer ua : userAnswers) {
                if (ua.getQuestionId().equals(q.getQuestionId()) && ua.getUserAnswer().equals(q.getOptionAnswer())) {
                    marksObtained += q.getQuestionMarks();
                }
            }
        }
        return marksObtained;
	}

	@Override
	public List<LmsTrainingQuestion> findByTrainingIdAndStatus(Integer trainingId, String status) {
		return tqRepo.findByTrainingIdAndStatus(trainingId,status);
	}

	@Override
	public LmsUserQuizDetails getquizDetails(Integer trainingId, Integer userRegId) {
		return qdRepo.getQuizDetails(trainingId, userRegId);
	}

	@Override
	public LmsUserQuizDetails getQuizResult(Integer trainingId, Integer userRegId) {
		return qdRepo.getQuizResult(trainingId, userRegId);
	}

}
