package com.springboot.training.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.training.entity.LMSTrainingDetails;


public interface CourseDtlRepository extends JpaRepository<LMSTrainingDetails, Integer>{

	Optional<LMSTrainingDetails> findById(Integer training_id);

	
}
