package com.springboot.training.service;

import java.util.List;
import java.util.Optional;

import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;
import com.springboot.training.entity.LmsUserQuizDetails;

public interface UserCourseService {

	List<LMSTrainingDetails> getUserCourse();

	Optional<LMSTrainingDetails> getCourseById(Integer Training_id);

	Integer calTotalMarks(Integer Training_id);

	boolean isUserPassed(Integer userRegId, Integer courseId);

	Integer getQuestionsAttempted(Integer trainingId, Integer userRegId);

	Integer calculateMarksObtained(Integer courseId, Integer userRegId);

	List<LmsTrainingQuestion> findByTrainingIdAndStatus(Integer trainingId, String status);

	LmsUserQuizDetails getquizDetails(Integer trainingId, Integer userRegId);
}
