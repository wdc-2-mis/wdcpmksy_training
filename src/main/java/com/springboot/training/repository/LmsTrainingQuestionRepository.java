package com.springboot.training.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	    @Query("UPDATE LmsTrainingQuestion SET status = :status WHERE trainingId = :trainingId AND q.status = 'D'")
	    void updateQuestionStatus(@Param("trainingId") Integer trainingId, @Param("status") String status);

	    Page<LmsTrainingQuestion> findByTrainingId(Integer trainingId, PageRequest pageable);
	    
	    List<LmsTrainingQuestion> findByTrainingIdAndStatus(Integer trainingId, String status);
	 



}
