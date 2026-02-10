package com.springboot.training.service;


import com.springboot.training.entity.YoutubeVideos;
import com.springboot.training.repository.YoutubeVideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
	public class YoutubeVideoService {

	    @Autowired
	    private YoutubeVideoRepository repository;

	    public Page<YoutubeVideos> getVideos(Pageable pageable) {
	    	return repository.findAll(pageable);
}
	}


