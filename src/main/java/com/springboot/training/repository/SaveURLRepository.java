package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.training.entity.URLDetails;

public interface SaveURLRepository extends JpaRepository<URLDetails, Long>{

}
