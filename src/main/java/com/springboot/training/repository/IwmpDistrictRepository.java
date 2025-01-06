package com.springboot.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.training.entity.IwmpDistrict;

public interface IwmpDistrictRepository extends JpaRepository<IwmpDistrict, Integer> {
	
    @Query("SELECT d FROM IwmpDistrict d WHERE d.stCode = :stCode ORDER BY d.distName")
    List<IwmpDistrict> findByStateCode(@Param("stCode") Integer stCode);
    
}