package com.springboot.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.training.entity.LMSTrainingDetails;


public interface CourseDtlRepository extends JpaRepository<LMSTrainingDetails, Integer>{

	Optional<LMSTrainingDetails> findById(Integer training_id);

	@Query(value = "SELECT * FROM lms_training_details c WHERE c.training_id NOT IN " + "(SELECT training_id FROM lms_training_question WHERE status = 'C')",
	nativeQuery = true)List<LMSTrainingDetails> findCoursesdtl();
}
