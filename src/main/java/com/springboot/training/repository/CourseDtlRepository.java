package com.springboot.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.training.entity.LMSTrainingDetails;
import com.springboot.training.entity.LmsTrainingQuestion;


public interface CourseDtlRepository extends JpaRepository<LMSTrainingDetails, Integer>{

	Optional<LMSTrainingDetails> findById(Integer training_id);

	@Query(value = "SELECT * FROM lms_training_details c WHERE c.training_id NOT IN " + "(SELECT training_id FROM lms_training_question WHERE status = 'C')",
	nativeQuery = true)List<LMSTrainingDetails> findCoursesdtl();

	@Query(value = "select status from lms_training_details where training_id = :trainingId", nativeQuery = true)
    String getTrainingStatus(@Param("trainingId") Integer trainingId);

	@Modifying
	@Query("UPDATE LMSTrainingDetails SET status = :status WHERE training_id = :trainingId")
	void updateTrainingStatus(@Param("trainingId") Integer trainingId, @Param("status") String status);

	
	
}
