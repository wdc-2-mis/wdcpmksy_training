package com.springboot.training.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media_coverage_videos")
public class MediaCoverageVideos {
	
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "videos_id", nullable = false)
    private Long videosId;

    @Column(name = "video_url")
    private String videoUrl;

	public Long getVideosId() {
		return videosId;
	}

	public void setVideosId(Long videosId) {
		this.videosId = videosId;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}


}
