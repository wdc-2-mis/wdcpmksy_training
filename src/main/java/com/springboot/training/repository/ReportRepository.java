package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.training.entity.URLDetails;

public interface ReportRepository extends JpaRepository<URLDetails, Long> {

	
}
