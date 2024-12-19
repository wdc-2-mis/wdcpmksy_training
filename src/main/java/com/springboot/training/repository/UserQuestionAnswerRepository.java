package com.springboot.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.training.entity.LmsUserQuestionAnswer;

@Repository
public interface UserQuestionAnswerRepository extends JpaRepository<LmsUserQuestionAnswer, Integer> {
	
	@Query("SELECT COUNT(uqa) FROM LmsUserQuestionAnswer uqa WHERE uqa.trainingId = :trainingId AND uqa.userRegId = :userRegId")
	Integer getQuestionsAttempted(Integer trainingId, Integer userRegId);
	
	@Query("SELECT uqa FROM LmsUserQuestionAnswer uqa WHERE uqa.trainingId = :trainingId AND uqa.userRegId = :userRegId")
	List<LmsUserQuestionAnswer> findUserAnswers(Integer trainingId, Integer userRegId);
	
}