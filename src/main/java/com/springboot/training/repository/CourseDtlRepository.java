package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.training.entity.LMSTrainingDetails;


public interface CourseDtlRepository extends JpaRepository<LMSTrainingDetails, Integer>{

	
}
