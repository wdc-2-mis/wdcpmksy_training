package com.springboot.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.training.entity.LmsUserQuizDetails;

@Repository
public interface LmsUserQuizDetailsRepository extends JpaRepository<LmsUserQuizDetails, Integer> {
	
	List<LmsUserQuizDetails> findByTrainingIdAndUserRegId(Integer trainingId, Integer userRegId);

}

