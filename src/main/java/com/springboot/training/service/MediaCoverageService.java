package com.springboot.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.training.entity.MediaCoverageVideos;
import com.springboot.training.repository.MediaCoverageRepository;

@Service
public class MediaCoverageService {
	
	@Autowired
    private MediaCoverageRepository repository;

    public List<MediaCoverageVideos> getAllVideos() {
        return repository.findAll();
    }

}
