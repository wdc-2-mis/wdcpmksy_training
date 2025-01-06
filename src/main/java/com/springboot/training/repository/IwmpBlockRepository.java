package com.springboot.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.training.entity.IwmpBlock;
import com.springboot.training.entity.IwmpDistrict;

public interface IwmpBlockRepository extends JpaRepository<IwmpBlock, Integer> {

	@Query("SELECT b FROM IwmpBlock b WHERE b.dcode = :districtCode ORDER BY b.blockName")
    List<IwmpBlock> findByDistrictCode(@Param("districtCode") Integer districtCode);
}