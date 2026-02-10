package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.training.entity.LmsSecurityQuestion;

public interface LmsSecurityQuesRepository extends JpaRepository<LmsSecurityQuestion, Integer>{

	
	
}
