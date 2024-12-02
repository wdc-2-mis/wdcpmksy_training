package com.springboot.training.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.training.dto.CourseDetails;
import com.springboot.training.dto.UserDto;
import com.springboot.training.dto.UserUrlDto;
import com.springboot.training.entity.URLDetails;
import com.springboot.training.entity.User;

import jakarta.validation.Valid;


public interface UserService {
	void saveUser(UserDto userDto);

    User findUserByEmail(String email);
    List<UserDto> findAllUsers();

    void saveUserUrl(UserUrlDto userUrlDto);

	List<UserUrlDto> findAllDetails(int PageNo, int PageSize);
	
	Page<URLDetails> findPaginated(int PageNo, int PageSize);

	void saveCourseDetailUrl(CourseDetails courseDetails, String ext, String file_name, String filePath);
}
