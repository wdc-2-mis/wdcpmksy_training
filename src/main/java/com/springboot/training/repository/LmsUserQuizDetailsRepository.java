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
	LmsUserQuizDetails getQuizResult(Integer trainingId, Integer userRegId);
	
	@Query(value = "SELECT * FROM (SELECT q.*,ROW_NUMBER() OVER (ORDER BY q.marks_obtained DESC) as row_num FROM lms_user_quiz_details q WHERE q.training_id = :trainingId AND q.user_reg_id = :userRegId AND q.status = 'P') AS passed_attempts UNION ALL SELECT * FROM (SELECT q.*, ROW_NUMBER() OVER (ORDER BY q.created_date DESC, q.user_quiz_id DESC) as row_num FROM lms_user_quiz_details q WHERE q.training_id = :trainingId AND q.user_reg_id = :userRegId AND q.status = 'F') AS failed_attempts ORDER BY row_num LIMIT 1", nativeQuery = true)
	LmsUserQuizDetails getQuizDetails(Integer trainingId, Integer userRegId);
	
//	@Query("SELECT q FROM LmsUserQuizDetails q WHERE q.trainingId = :trainingId AND q.userRegId = :userRegId AND ((q.status = 'F' AND q.user_quiz_id = (SELECT MAX(user_quiz_id) FROM LmsUserQuizDetails WHERE trainingId = :trainingId AND userRegId = :userRegId AND status = 'F')) OR (q.status = 'P' AND q.marksObtained = (SELECT MAX(marksObtained) FROM LmsUserQuizDetails WHERE trainingId = :trainingId AND userRegId = :userRegId AND status = 'P'))) ORDER BY q.createdDate DESC, q.user_quiz_id DESC LIMIT 1")
//	LmsUserQuizDetails getQuizDetails(Integer trainingId, Integer userRegId);

}

