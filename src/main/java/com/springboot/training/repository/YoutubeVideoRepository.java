package com.springboot.training.repository;



import com.springboot.training.entity.YoutubeVideos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface YoutubeVideoRepository extends JpaRepository<YoutubeVideos, Long> {

	
}
