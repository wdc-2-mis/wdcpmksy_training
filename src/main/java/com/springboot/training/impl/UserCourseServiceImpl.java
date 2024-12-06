package com.springboot.training.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.repository.QuestionRepository;
import com.springboot.training.repository.UserCourseRepository;
import com.springboot.training.service.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {

	@Autowired
	private UserCourseRepository ucRepo;

	@Autowired
	private QuestionRepository tqRepo;

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
		List<LmsTrainingQuestion> ques = tqRepo.findByTrainingId(Training_id);
//		Integer i=0;
//		for (LmsTrainingQuestion temp : ques) {
//			i=i+temp.getQuestionMarks();
//		}
//		return i;		

		return ques.stream().map(LmsTrainingQuestion::getQuestionMarks).reduce(0, Integer::sum);

	}

	@Override
	public boolean isUserPassed(String userId, Integer courseId) {
		Integer userScore = ucRepo.getUserScore(userId, courseId);
		Integer minPassMarks = ucRepo.getMinPassMarks(courseId);

		return userScore != null && userScore >= minPassMarks;
	}

}
