package com.springboot.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.training.entity.LmsTrainingQuestion;

public interface QuestionRepository extends JpaRepository<LmsTrainingQuestion, Integer> {
	
	List<LmsTrainingQuestion> findByTrainingId(Integer training_id);

	List<LmsTrainingQuestion> findByTrainingIdAndStatus(Integer trainingId, String status);
	
}
