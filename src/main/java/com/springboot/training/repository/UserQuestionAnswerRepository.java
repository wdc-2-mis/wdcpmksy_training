package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.training.entity.LmsUserQuestionAnswer;

@Repository
public interface UserQuestionAnswerRepository extends JpaRepository<LmsUserQuestionAnswer, Integer> {
	
}