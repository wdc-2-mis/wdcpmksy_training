package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.training.entity.MediaCoverageVideos;

@Repository
public interface MediaCoverageRepository extends JpaRepository<MediaCoverageVideos, Long>{

}
