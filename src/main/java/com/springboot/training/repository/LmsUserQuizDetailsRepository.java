package com.springboot.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.training.entity.LmsUserQuizDetails;

@Repository
public interface LmsUserQuizDetailsRepository extends JpaRepository<LmsUserQuizDetails, Integer> {
	
	List<LmsUserQuizDetails> findByTrainingIdAndUserRegId(Integer trainingId, Integer userRegId);
	
	@Query("SELECT q FROM LmsUserQuizDetails q WHERE q.trainingId = :trainingId AND q.userRegId = :userRegId ORDER BY q.createdDate DESC, q.user_quiz_id DESC LIMIT 1")
	LmsUserQuizDetails getQuizDetails(Integer trainingId, Integer userRegId);

}

