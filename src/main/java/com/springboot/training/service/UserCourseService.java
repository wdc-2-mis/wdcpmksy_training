package com.springboot.training.service;

import java.util.List;
import java.util.Optional;

import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;

public interface UserCourseService {

	List<LMSTrainingDetails> getUserCourse();

	Optional<LMSTrainingDetails> getCourseById(Integer Training_id);

	Integer calTotalMarks(Integer Training_id);

	boolean isUserPassed(String userId, Integer courseId);
}
