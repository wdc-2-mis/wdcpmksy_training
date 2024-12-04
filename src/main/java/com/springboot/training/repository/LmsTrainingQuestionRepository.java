package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.training.entity.LmsTrainingQuestion;


public interface LmsTrainingQuestionRepository extends JpaRepository<LmsTrainingQuestion, Integer>{

	    @Query("SELECT COUNT(q) FROM LmsTrainingQuestion q WHERE q.trainingId = :trainingId")
	    int countQuestionsByTrainingId(@Param("trainingId") Integer trainingId);

	    @Transactional
	    @Modifying
	    @Query("UPDATE LmsTrainingQuestion q SET q.status = 'C' WHERE q.trainingId = :trainingId AND q.status = 'D'")
	    void finalizeDraftQuestions(@Param("trainingId") Integer trainingId);

	    
	
}
