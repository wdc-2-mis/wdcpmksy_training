package com.springboot.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.training.entity.IwmpState;

public interface IwmpStateRepository extends JpaRepository<IwmpState, Integer>{

	@Query("SELECT s FROM IwmpState s WHERE s.wdcpmksy = 1 ORDER BY s.stName")
    List<IwmpState> findAllStates();
}
