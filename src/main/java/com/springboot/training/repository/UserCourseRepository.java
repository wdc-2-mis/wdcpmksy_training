package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.training.entity.LMSTrainingDetails;

@Repository
public interface UserCourseRepository extends JpaRepository<LMSTrainingDetails, Integer> {

	@Query("SELECT SUM(CASE WHEN ua.lmsTrainingQuestion.optionAnswer = ua.userAnswer THEN ua.lmsTrainingQuestion.questionMarks ELSE 0 END) "
			+ "FROM LmsUserQuestionAnswer ua "
			+ "WHERE ua.trainingDetails.training_id = :courseId AND ua.user.user_reg_id = :userRegId")
	Integer getUserScore(@Param("userRegId") Integer userRegId, @Param("courseId") Integer courseId);

	@Query("SELECT td.min_pass_marks FROM LMSTrainingDetails td WHERE td.training_id = :courseId")
	Integer getMinPassMarks(@Param("courseId") Integer courseId);

}
